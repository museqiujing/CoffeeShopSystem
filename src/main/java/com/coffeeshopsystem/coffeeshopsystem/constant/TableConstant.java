package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 餐桌相关常量
 */
public interface TableConstant {
    // 餐桌状态
    Integer STATUS_FREE = 0;        // 空闲
    Integer STATUS_OCCUPIED = 1;    // 占用
    Integer STATUS_RESERVED = 2;    // 预订
    Integer STATUS_CLEANING = 3;    // 清理中
}