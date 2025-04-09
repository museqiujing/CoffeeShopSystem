package com.coffeeshopsystem.coffeeshopsystem.util;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应结果类
 *
 * @author museqiujing
 * @since 2025-04-08
 */
@Data
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    // 成功静态方法
    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    // 失败静态方法
    public static <T> Result<T> error() {
        return error(500, "服务器错误");
    }

    public static <T> Result<T> error(String message) {
        return error(500, message);
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}