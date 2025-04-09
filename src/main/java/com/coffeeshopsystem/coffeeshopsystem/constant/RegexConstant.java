package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 正则表达式常量
 */
public interface RegexConstant {
    // 用户名正则：4-16位字母、数字、下划线
    String USERNAME_REGEX = "^[a-zA-Z0-9_]{4,16}$";

    // 密码正则：6-20位字母、数字、特殊字符
    String PASSWORD_REGEX = "^[a-zA-Z0-9!@#$%^&*()_+]{6,20}$";

    // 手机号正则
    String PHONE_REGEX = "^1[3-9]\\d{9}$";

    // 邮箱正则
    String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    // 金额正则：最多两位小数的正数
    String MONEY_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
}