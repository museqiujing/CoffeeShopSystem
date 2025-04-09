package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.Order;
import com.coffeeshopsystem.coffeeshopsystem.entity.OrderItem;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends IService<Order> {
    // 创建订单
    Order createOrder(Order order, List<OrderItem> items);

    // 更新订单状态
    void updateOrderStatus(Integer id, String status);

    // 更新支付状态
    void updatePaymentStatus(Integer id, String paymentStatus, String paymentMethod);

    // 获取指定用户的订单
    List<Order> getUserOrders(Integer userId);

    // 获取指定状态的订单
    List<Order> getOrdersByStatus(String status);

    // 获取指定时间范围的订单
    List<Order> getOrdersByDateRange(LocalDateTime startTime, LocalDateTime endTime);

    // 获取指定餐桌的订单
    List<Order> getTableOrders(Integer tableId);

    // 分页查询订单
    Page<Order> getOrderPage(Integer pageNum, Integer pageSize, String orderType, String status);

    // 取消订单
    void cancelOrder(Integer id, String reason);
}