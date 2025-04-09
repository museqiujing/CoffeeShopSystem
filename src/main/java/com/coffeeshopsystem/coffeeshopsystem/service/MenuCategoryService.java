package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.MenuCategory;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import java.util.List;

public interface MenuCategoryService extends IService<MenuCategory> {
    // 添加分类
    Result<Void> addCategory(String name, String description, String categoryType, Integer sortOrder);

    // 更新分类
    Result<Void> updateCategory(Integer id, String name, String description, String categoryType);

    // 删除分类
    Result<Void> deleteCategory(Integer id);

    // 分页查询
    Result<Page<MenuCategory>> getCategoryPage(Integer pageNum, Integer pageSize);

    // 更新排序
    Result<Void> updateSortOrder(Integer id, Integer sortOrder);

    // 根据分类类型获取分类
    Result<List<MenuCategory>> getCategoriesByType(String categoryType);
}