package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coffeeshopsystem.coffeeshopsystem.entity.MenuCategory;
import com.coffeeshopsystem.coffeeshopsystem.entity.Menu;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MenuCategoryMapper;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MenuMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.MenuCategoryService;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MenuCategoryServiceImpl extends ServiceImpl<MenuCategoryMapper, MenuCategory> implements MenuCategoryService {

    private static final List<String> VALID_CATEGORY_TYPES = Arrays.asList("DESSERT", "COFFEE", "OTHER");
    private final MenuMapper menuMapper;
    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> addCategory(String name, String description, String categoryType, Integer sortOrder) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (!StringUtils.hasText(name)) {
                return Result.error("分类名称不能为空");
            }
            if (!VALID_CATEGORY_TYPES.contains(categoryType)) {
                return Result.error("无效的分类类型");
            }
            if (sortOrder == null || sortOrder < 0) {
                return Result.error("排序值无效");
            }

            // 检查名称是否重复
            long count = this.lambdaQuery()
                    .eq(MenuCategory::getName, name)
                    .count();

            if (count > 0) {
                return Result.error("分类名称已存在");
            }

            // 创建新分类
            MenuCategory category = new MenuCategory();
            category.setName(name);
            category.setDescription(description);
            category.setCategoryType(categoryType);
            category.setSortOrder(sortOrder);

            this.save(category);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("添加分类失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateCategory(Integer id, String name, String description, String categoryType) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (!StringUtils.hasText(name)) {
                return Result.error("分类名称不能为空");
            }
            if (!VALID_CATEGORY_TYPES.contains(categoryType)) {
                return Result.error("无效的分类类型");
            }

            MenuCategory category = this.getById(id);
            if (category == null) {
                return Result.error("分类不存在");
            }

            // 检查名称是否与其他分类重复
            long count = this.lambdaQuery()
                    .eq(MenuCategory::getName, name)
                    .ne(MenuCategory::getId, id)
                    .count();

            if (count > 0) {
                return Result.error("分类名称已存在");
            }

            category.setName(name);
            category.setDescription(description);
            category.setCategoryType(categoryType);

            this.updateById(category);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新分类失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> deleteCategory(Integer id) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 检查分类是否存在
            MenuCategory category = this.getById(id);
            if (category == null) {
                return Result.error("分类不存在");
            }

            // 检查是否存在关联的菜品
            long count = menuMapper.selectCount(
                    new LambdaQueryWrapper<Menu>()
                            .eq(Menu::getCategoryId, id)
            );

            if (count > 0) {
                return Result.error("该分类下存在菜品，不能删除");
            }

            this.removeById(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("删除分类失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Page<MenuCategory>> getCategoryPage(Integer pageNum, Integer pageSize) {
        try {
            Page<MenuCategory> page = this.lambdaQuery()
                    .orderByAsc(MenuCategory::getSortOrder)
                    .page(new Page<>(pageNum, pageSize));

            return Result.ok(page);
        } catch (Exception e) {
            return Result.error("获取分类列表失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateSortOrder(Integer id, Integer sortOrder) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (sortOrder == null || sortOrder < 0) {
                return Result.error("排序值无效");
            }

            MenuCategory category = this.getById(id);
            if (category == null) {
                return Result.error("分类不存在");
            }

            category.setSortOrder(sortOrder);
            this.updateById(category);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新排序失败：" + e.getMessage());
        }
    }

    @Override
    public Result<List<MenuCategory>> getCategoriesByType(String categoryType) {
        try {
            if (!VALID_CATEGORY_TYPES.contains(categoryType)) {
                return Result.error("无效的分类类型");
            }

            List<MenuCategory> categories = this.lambdaQuery()
                    .eq(MenuCategory::getCategoryType, categoryType)
                    .orderByAsc(MenuCategory::getSortOrder)
                    .list();

            return Result.ok(categories);
        } catch (Exception e) {
            return Result.error("获取分类列表失败：" + e.getMessage());
        }
    }

    // 检查是否为员工或管理员
    private boolean isStaffOrAdmin() {
        Object role = request.getAttribute("role");
        return "STAFF".equals(role) || "ADMIN".equals(role);
    }
}