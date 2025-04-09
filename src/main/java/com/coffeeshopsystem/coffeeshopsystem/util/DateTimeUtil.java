package com.coffeeshopsystem.coffeeshopsystem.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类
 *
 * @author museqiujing
 * @since 2025-04-08
 */
public class DateTimeUtil {
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final ZoneId ZONE_ID = ZoneId.of("UTC+8");

    /**
     * 格式化日期时间
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
    }

    /**
     * 格式化日期
     */
    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    /**
     * 获取当前日期时间
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(ZONE_ID);
    }

    /**
     * 字符串转日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
    }
}