package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryCancelDTO {
    @NotNull(message = "配送单ID不能为空")
    private Integer id;

    @NotBlank(message = "取消原因不能为空")
    private String reason;
}