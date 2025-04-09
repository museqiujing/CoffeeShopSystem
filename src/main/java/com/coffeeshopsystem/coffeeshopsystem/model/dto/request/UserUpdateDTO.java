package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private String phone;
    private String address;
    private String avatarUrl;

    // 使用项目中已有的正则工具类的正则表达式
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    public String getPhone() {
        return phone;
    }
}