package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DeliveryStatusUpdateDTO {
    @NotNull(message = "配送单ID不能为空")
    private Integer id;

    @NotBlank(message = "配送状态不能为空")
    @Pattern(regexp = "^(PENDING|PICKING|DELIVERING|COMPLETED|CANCELLED)$",
            message = "无效的配送状态")
    private String status;
}