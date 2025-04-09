package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.entity.User;
import com.coffeeshopsystem.coffeeshopsystem.mapper.UserMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.UserService;
import com.coffeeshopsystem.coffeeshopsystem.util.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    @Override
    public Result<String> login(String username, String password) {
        try {
            // 参数验证
            if (!RegexUtils.isPasswordValid(password)) {
                return Result.error("密码格式不正确");
            }

            // 根据用户名查询用户
            User user = this.lambdaQuery()
                    .eq(User::getUsername, username)
                    .one();

            if (user == null) {
                return Result.error("用户不存在");
            }

            // 检查用户状态
            if (user.getStatus() == 0) {
                return Result.error("账号已被禁用");
            }

            // 验证密码
            if (!SecurityUtils.matches(password, user.getPassword())) {
                return Result.error("密码错误");
            }

            // 生成token
            String token = jwtUtil.generateToken(user.getUsername(), user.getId(), user.getUserType());
            return Result.ok(token);
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> register(User user) {
        try {
            // 参数验证
            if (!RegexUtils.isPasswordValid(user.getPassword())) {
                return Result.error("密码必须至少8位，包含字母和数字");
            }
            if (!RegexUtils.isPhoneValid(user.getPhone())) {
                return Result.error("手机号格式不正确");
            }

            // 检查用户名是否存在
            long count = this.lambdaQuery()
                    .eq(User::getUsername, user.getUsername())
                    .count();

            if (count > 0) {
                return Result.error("用户名已存在");
            }

            // 设置默认值
            user.setUserType("CUSTOMER");
            user.setStatus(1);

            // 加密密码
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));

            // 保存用户
            this.save(user);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updatePassword(Integer userId, String oldPassword, String newPassword) {
        try {
            // 权限检查
            if (!isCurrentUserOrAdmin(userId)) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (!RegexUtils.isPasswordValid(newPassword)) {
                return Result.error("新密码必须至少8位，包含字母和数字");
            }

            // 获取用户
            User user = this.getById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }

            // 验证旧密码
            if (!SecurityUtils.matches(oldPassword, user.getPassword())) {
                return Result.error("原密码错误");
            }

            // 更新密码
            user.setPassword(SecurityUtils.encryptPassword(newPassword));
            this.updateById(user);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("修改密码失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateStatus(Integer userId, Integer status) {
        try {
            // 权限检查
            if (!isAdmin()) {
                return Result.error(403, "只有管理员可以执行此操作");
            }

            // 参数验证
            if (status != 0 && status != 1) {
                return Result.error("状态值无效");
            }

            User user = this.getById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }

            // 不能禁用管理员账号
            if (status == 0 && "ADMIN".equals(user.getUserType())) {
                return Result.error("不能禁用管理员账号");
            }

            user.setStatus(status);
            this.updateById(user);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新状态失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize, String keyword) {
        try {
            // 权限检查
            if (!isAdmin()) {
                return Result.error(403, "只有管理员可以查看用户列表");
            }

            Page<User> page = this.lambdaQuery()
                    .like(keyword != null, User::getUsername, keyword)
                    .or()
                    .like(keyword != null, User::getPhone, keyword)
                    .page(new Page<>(pageNum, pageSize));

            return Result.ok(page);
        } catch (Exception e) {
            return Result.error("查询用户列表失败：" + e.getMessage());
        }
    }

    @Override
    public Result<User> getCurrentUser() {
        try {
            Object userId = request.getAttribute("userId");
            if (userId == null) {
                return Result.error(401, "未登录");
            }

            User user = this.getById((Integer) userId);
            if (user == null) {
                return Result.error("用户不存在");
            }

            // 清除敏感信息
            user.setPassword(null);
            return Result.ok(user);
        } catch (Exception e) {
            return Result.error("获取当前用户信息失败：" + e.getMessage());
        }
    }

    // 检查是否为当前用户或管理员
    private boolean isCurrentUserOrAdmin() {
        Object role = request.getAttribute("role");
        Object userId = request.getAttribute("userId");
        return "ADMIN".equals(role) || userId != null;
    }

    // 检查是否为当前用户或管理员
    private boolean isCurrentUserOrAdmin(Integer targetUserId) {
        Object role = request.getAttribute("role");
        Object userId = request.getAttribute("userId");
        return "ADMIN".equals(role) || (userId != null && userId.equals(targetUserId));
    }

    // 检查是否为管理员
    private boolean isAdmin() {
        Object role = request.getAttribute("role");
        return "ADMIN".equals(role);
    }
}