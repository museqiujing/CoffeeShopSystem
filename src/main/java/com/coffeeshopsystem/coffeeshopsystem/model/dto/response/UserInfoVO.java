package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class UserInfoVO {
    private Integer id;
    private String username;
    private String phone;
    private String address;
    private String avatarUrl;
    private String userType;    // CUSTOMER, STAFF, ADMIN
    private Integer status;     // 1-正常，0-禁用
}