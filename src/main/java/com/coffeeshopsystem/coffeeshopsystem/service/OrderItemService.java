package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.OrderItem;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import java.util.List;
import java.math.BigDecimal;

public interface OrderItemService extends IService<OrderItem> {
    // 创建订单项
    Result<OrderItem> createOrderItem(Integer orderId, Integer menuId, Integer quantity, BigDecimal price);

    // 批量创建订单项
    Result<List<OrderItem>> createOrderItems(Integer orderId, List<OrderItem> items);

    // 获取订单的所有订单项
    Result<List<OrderItem>> getOrderItems(Integer orderId);

    // 更新订单项数量
    Result<Void> updateQuantity(Integer id, Integer quantity);

    // 计算订单项小计
    Result<BigDecimal> calculateSubtotal(Integer quantity, BigDecimal price);

    // 获取指定菜品在所有订单中的总数量
    Result<Integer> getTotalQuantityByMenuId(Integer menuId);
}