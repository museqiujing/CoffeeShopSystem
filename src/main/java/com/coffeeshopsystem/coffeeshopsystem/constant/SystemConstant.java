package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 系统常量
 */
public interface SystemConstant {
    // 分页默认值
    Integer DEFAULT_PAGE_SIZE = 10;
    Integer DEFAULT_PAGE_NUM = 1;

    // 时间格式
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String DATE_FORMAT = "yyyy-MM-dd";

    // 文件相关
    String FILE_UPLOAD_PATH = "uploads/";
    Integer MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    String ALLOWED_FILE_TYPES = "jpg,jpeg,png";
}