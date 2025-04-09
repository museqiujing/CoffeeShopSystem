package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 用户相关常量
 */
public interface UserConstant {
    // 用户状态
    Integer STATUS_NORMAL = 1;    // 正常
    Integer STATUS_DISABLED = 0;  // 禁用

    // 用户角色
    String ROLE_ADMIN = "ADMIN";       // 管理员
    String ROLE_STAFF = "STAFF";       // 员工
    String ROLE_CUSTOMER = "CUSTOMER"; // 顾客

    // 默认值
    String DEFAULT_AVATAR = "default.jpg";
    Integer DEFAULT_CREDIT = 0;
}