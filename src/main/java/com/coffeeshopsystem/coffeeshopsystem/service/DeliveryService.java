package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.Delivery;

import java.util.List;
import java.time.LocalDateTime;

public interface DeliveryService extends IService<Delivery> {
    // 创建配送订单
    void createDelivery(Delivery delivery);

    // 分配配送员
    void assignDeliverer(Integer id, Integer delivererId);

    // 更新配送状态
    void updateStatus(Integer id, String status);

    // 获取配送员的当前配送任务
    List<Delivery> getCurrentDeliveries(Integer delivererId);

    // 分页查询配送订单
    Page<Delivery> getDeliveryPage(Integer pageNum, Integer pageSize, String status);

    // 获取指定时间段内的配送订单
    List<Delivery> getDeliveriesByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    // 获取待分配的配送订单
    List<Delivery> getPendingDeliveries();

    // 完成配送
    void completeDelivery(Integer id);

    // 取消配送
    void cancelDelivery(Integer id, String reason);
}