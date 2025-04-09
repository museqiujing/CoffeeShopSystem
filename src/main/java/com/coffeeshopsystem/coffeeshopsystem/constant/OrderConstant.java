package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 订单相关常量
 */
public interface OrderConstant {
    // 订单状态
    Integer STATUS_PENDING = 0;     // 待处理
    Integer STATUS_PROCESSING = 1;   // 处理中
    Integer STATUS_COMPLETED = 2;    // 已完成
    Integer STATUS_CANCELLED = 3;    // 已取消

    // 支付状态
    Integer PAY_STATUS_UNPAID = 0;   // 未支付
    Integer PAY_STATUS_PAID = 1;     // 已支付
    Integer PAY_STATUS_REFUNDED = 2; // 已退款

    // 订单类型
    Integer TYPE_DINE_IN = 1;        // 堂食
    Integer TYPE_TAKEOUT = 2;        // 外卖

    // 默认值
    Integer DEFAULT_PRIORITY = 0;     // 默认优先级
}