package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 原料相关常量
 */
public interface MaterialConstant {
    // 原料状态
    Integer STATUS_NORMAL = 1;       // 正常
    Integer STATUS_WARNING = 2;      // 警告
    Integer STATUS_SHORTAGE = 3;     // 短缺

    // 操作类型
    Integer OPERATION_IN = 1;        // 入库
    Integer OPERATION_OUT = 2;       // 出库
    Integer OPERATION_CHECK = 3;     // 盘点
}