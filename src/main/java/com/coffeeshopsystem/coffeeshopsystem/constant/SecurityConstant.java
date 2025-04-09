package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 安全相关常量
 */
public interface SecurityConstant {
    // JWT相关
    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String SECRET_KEY = "CoffeeShopSystemSecretKey";
    Long EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24小时

    // 加密相关
    Integer SALT_LENGTH = 16;
    Integer PASSWORD_MIN_LENGTH = 6;
    Integer PASSWORD_MAX_LENGTH = 20;
}