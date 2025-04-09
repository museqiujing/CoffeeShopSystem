package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class LoginVO {
    private String token;
    private UserInfoVO userInfo;
}