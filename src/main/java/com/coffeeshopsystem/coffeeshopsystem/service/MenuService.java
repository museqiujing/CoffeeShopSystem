package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.Menu;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import java.math.BigDecimal;
import java.util.List;

public interface MenuService extends IService<Menu> {
    // 添加菜品
    Result<Void> addMenu(Menu menu);

    // 更新菜品
    Result<Void> updateMenu(Menu menu);

    // 更新菜品状态
    Result<Void> updateStatus(Integer id, Integer status);

    // 更新库存
    Result<Void> updateStock(Integer id, Integer stock);

    // 减少库存
    Result<Void> decreaseStock(Integer id, Integer quantity);

    // 增加销量
    Result<Void> increaseSalesCount(Integer id, Integer quantity);

    // 分页查询菜品
    Result<Page<Menu>> getMenuPage(Integer pageNum, Integer pageSize, String keyword);

    // 根据分类查询菜品
    Result<List<Menu>> getMenuByCategory(Integer categoryId);

    // 获取热销菜品
    Result<List<Menu>> getHotSaleMenu(Integer limit);

    // 获取可用菜品
    Result<List<Menu>> getAvailableMenu();

    // 更新价格
    Result<Void> updatePrice(Integer id, BigDecimal price);
}