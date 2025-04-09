package com.coffeeshopsystem.coffeeshopsystem.util;

import java.util.regex.Pattern;

/**
 * 正则验证工具类
 *
 * @author museqiujing
 * @since 2025-04-08
 */
public class RegexUtils {
    /**
     * 手机号正则
     */
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 邮箱正则
     */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    /**
     * 密码正则（至少8位，包含字母和数字）
     */
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    /**
     * 验证手机号
     */
    public static boolean isPhoneValid(String phone) {
        return phone != null && Pattern.matches(PHONE_REGEX, phone);
    }

    /**
     * 验证邮箱
     */
    public static boolean isEmailValid(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    /**
     * 验证密码
     */
    public static boolean isPasswordValid(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }
}