package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 响应状态码常量
 */
public interface ResultCode {
    // 成功
    Integer SUCCESS = 200;
    // 失败
    Integer ERROR = 500;
    // 未授权
    Integer UNAUTHORIZED = 401;
    // 禁止访问
    Integer FORBIDDEN = 403;
    // 参数校验失败
    Integer VALIDATE_FAILED = 400;
    // 未找到
    Integer NOT_FOUND = 404;
}