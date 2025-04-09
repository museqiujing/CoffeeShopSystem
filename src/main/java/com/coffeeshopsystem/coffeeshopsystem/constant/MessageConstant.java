package com.coffeeshopsystem.coffeeshopsystem.constant;

/**
 * 消息常量
 */
public interface MessageConstant {
    // 通用消息
    String SUCCESS = "操作成功";
    String ERROR = "操作失败";
    String SYSTEM_ERROR = "系统错误";
    String PARAM_ERROR = "参数错误";

    // 认证相关消息
    String LOGIN_SUCCESS = "登录成功";
    String LOGIN_FAILED = "登录失败";
    String LOGOUT_SUCCESS = "登出成功";
    String UNAUTHORIZED = "未授权，请先登录";
    String FORBIDDEN = "无权限访问";
    String TOKEN_EXPIRED = "token已过期";
    String TOKEN_INVALID = "无效的token";

    // 用户相关消息
    String USER_NOT_FOUND = "用户不存在";
    String USERNAME_EXISTS = "用户名已存在";
    String PASSWORD_ERROR = "密码错误";
    String ACCOUNT_DISABLED = "账号已被禁用";

    // 订单相关消息
    String ORDER_NOT_FOUND = "订单不存在";
    String ORDER_STATUS_ERROR = "订单状态错误";
    String ORDER_ALREADY_PAID = "订单已支付";
    String ORDER_NOT_PAID = "订单未支付";

    // 库存相关消息
    String STOCK_NOT_ENOUGH = "库存不足";
    String STOCK_WARNING = "库存警告";
}