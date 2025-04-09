package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 菜单相关常量
 */
public interface MenuConstant {
    // 菜品状态
    Integer STATUS_AVAILABLE = 1;    // 上架
    Integer STATUS_UNAVAILABLE = 0;  // 下架

    // 推荐状态
    Integer RECOMMEND_YES = 1;       // 推荐
    Integer RECOMMEND_NO = 0;        // 不推荐

    // 默认值
    String DEFAULT_IMAGE = "default-menu.jpg";
}