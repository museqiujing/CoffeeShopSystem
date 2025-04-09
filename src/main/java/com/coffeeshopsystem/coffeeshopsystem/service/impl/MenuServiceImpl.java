package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coffeeshopsystem.coffeeshopsystem.entity.Menu;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MenuMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.MenuService;
import com.coffeeshopsystem.coffeeshopsystem.util.DateTimeUtil;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> addMenu(Menu menu) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (!validateMenuParams(menu)) {
                return Result.error("参数验证失败");
            }

            // 检查名称是否重复
            long count = this.lambdaQuery()
                    .eq(Menu::getName, menu.getName())
                    .count();

            if (count > 0) {
                return Result.error("菜品名称已存在");
            }

            // 设置初始值
            menu.setSalesCount(0);
            menu.setStatus(1);
            menu.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

            this.save(menu);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("添加菜品失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateMenu(Menu menu) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (!validateMenuParams(menu)) {
                return Result.error("参数验证失败");
            }

            Menu existingMenu = this.getById(menu.getId());
            if (existingMenu == null) {
                return Result.error("菜品不存在");
            }

            // 检查名称是否与其他菜品重复
            long count = this.lambdaQuery()
                    .eq(Menu::getName, menu.getName())
                    .ne(Menu::getId, menu.getId())
                    .count();

            if (count > 0) {
                return Result.error("菜品名称已存在");
            }

            menu.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
            this.updateById(menu);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新菜品失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateStatus(Integer id, Integer status) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            if (status != 0 && status != 1) {
                return Result.error("无效的状态值");
            }

            Menu menu = this.getById(id);
            if (menu == null) {
                return Result.error("菜品不存在");
            }

            menu.setStatus(status);
            menu.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
            this.updateById(menu);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新状态失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateStock(Integer id, Integer stock) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            if (stock < 0) {
                return Result.error("库存不能小于0");
            }

            Menu menu = this.getById(id);
            if (menu == null) {
                return Result.error("菜品不存在");
            }

            menu.setStock(stock);
            menu.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
            this.updateById(menu);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新库存失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> decreaseStock(Integer id, Integer quantity) {
        try {
            if (quantity <= 0) {
                return Result.error("数量必须大于0");
            }

            Menu menu = this.getById(id);
            if (menu == null) {
                return Result.error("菜品不存在");
            }

            if (menu.getStock() < quantity) {
                return Result.error("库存不足");
            }

            menu.setStock(menu.getStock() - quantity);
            menu.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
            this.updateById(menu);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("减少库存失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> increaseSalesCount(Integer id, Integer quantity) {
        try {
            Menu menu = this.getById(id);
            if (menu == null) {
                return Result.error("菜品不存在");
            }

            menu.setSalesCount(menu.getSalesCount() + quantity);
            menu.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
            this.updateById(menu);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新销量失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Page<Menu>> getMenuPage(Integer pageNum, Integer pageSize, String keyword) {
        try {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(keyword)) {
                queryWrapper.like(Menu::getName, keyword)
                        .or()
                        .like(Menu::getDescription, keyword);
            }
            queryWrapper.orderByDesc(Menu::getUpdatedAt);

            Page<Menu> page = this.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.ok(page);
        } catch (Exception e) {
            return Result.error("查询菜品列表失败：" + e.getMessage());
        }
    }

    @Override
    public Result<List<Menu>> getMenuByCategory(Integer categoryId) {
        try {
            List<Menu> menus = this.lambdaQuery()
                    .eq(Menu::getCategoryId, categoryId)
                    .eq(Menu::getStatus, 1)
                    .orderByDesc(Menu::getSalesCount)
                    .list();
            return Result.ok(menus);
        } catch (Exception e) {
            return Result.error("查询分类菜品失败：" + e.getMessage());
        }
    }

    @Override
    public Result<List<Menu>> getHotSaleMenu(Integer limit) {
        try {
            List<Menu> menus = this.lambdaQuery()
                    .eq(Menu::getStatus, 1)
                    .orderByDesc(Menu::getSalesCount)
                    .last("LIMIT " + limit)
                    .list();
            return Result.ok(menus);
        } catch (Exception e) {
            return Result.error("查询热销菜品失败：" + e.getMessage());
        }
    }

    @Override
    public Result<List<Menu>> getAvailableMenu() {
        try {
            List<Menu> menus = this.lambdaQuery()
                    .eq(Menu::getStatus, 1)
                    .gt(Menu::getStock, 0)
                    .orderByDesc(Menu::getUpdatedAt)
                    .list();
            return Result.ok(menus);
        } catch (Exception e) {
            return Result.error("查询可用菜品失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updatePrice(Integer id, BigDecimal price) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            if (price.compareTo(BigDecimal.ZERO) < 0) {
                return Result.error("价格不能小于0");
            }

            Menu menu = this.getById(id);
            if (menu == null) {
                return Result.error("菜品不存在");
            }

            menu.setPrice(price);
            menu.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
            this.updateById(menu);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新价格失败：" + e.getMessage());
        }
    }

    // 验证菜品参数
    private boolean validateMenuParams(Menu menu) {
        if (!StringUtils.hasText(menu.getName())) {
            return false;
        }
        if (menu.getPrice() == null || menu.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (menu.getCategoryId() == null) {
            return false;
        }
        if (menu.getStock() == null || menu.getStock() < 0) {
            return false;
        }
        return true;
    }

    // 检查是否为员工或管理员
    private boolean isStaffOrAdmin() {
        Object role = request.getAttribute("role");
        return "STAFF".equals(role) || "ADMIN".equals(role);
    }
}