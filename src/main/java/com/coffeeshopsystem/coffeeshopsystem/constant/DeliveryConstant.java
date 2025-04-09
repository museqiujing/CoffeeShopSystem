package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 配送相关常量
 */
public interface DeliveryConstant {
    // 配送状态
    Integer STATUS_WAITING = 0;      // 待配送
    Integer STATUS_DELIVERING = 1;    // 配送中
    Integer STATUS_COMPLETED = 2;     // 已送达
    Integer STATUS_CANCELLED = 3;     // 已取消

    // 默认值
    Integer DEFAULT_RANGE = 5000;     // 默认配送范围(米)
}