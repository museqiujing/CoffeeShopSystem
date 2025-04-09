package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * Redis键常量
 */
public interface RedisConstant {
    // 用户token前缀
    String TOKEN_PREFIX = "token:";

    // 验证码前缀
    String VERIFY_CODE_PREFIX = "verify:code:";

    // 用户信息前缀
    String USER_INFO_PREFIX = "user:info:";

    // 菜单信息前缀
    String MENU_INFO_PREFIX = "menu:info:";

    // 过期时间（秒）
    Long TOKEN_EXPIRE = 24 * 60 * 60L;        // 24小时
    Long VERIFY_CODE_EXPIRE = 5 * 60L;        // 5分钟
    Long USER_INFO_EXPIRE = 30 * 60L;         // 30分钟
    Long MENU_INFO_EXPIRE = 12 * 60 * 60L;    // 12小时
}