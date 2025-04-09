package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.User;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;

public interface UserService extends IService<User> {
    // 用户登录
    Result<String> login(String username, String password);

    // 用户注册
    Result<Void> register(User user);

    // 修改密码
    Result<Void> updatePassword(Integer userId, String oldPassword, String newPassword);

    // 更新用户状态
    Result<Void> updateStatus(Integer userId, Integer status);

    // 分页查询用户
    Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize, String keyword);

    // 获取当前登录用户信息
    Result<User> getCurrentUser();
}