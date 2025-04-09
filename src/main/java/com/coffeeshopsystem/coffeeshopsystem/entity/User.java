package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String address;
    private String avatarUrl;
    private String userType;  // CUSTOMER, STAFF, ADMIN
    private Integer status;   // 1-正常，0-禁用
}