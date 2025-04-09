package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Delivery;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryAssignDTO {
    @NotNull(message = "配送单ID不能为空")
    private Integer id;

    @NotNull(message = "配送员ID不能为空")
    private Integer delivererId;
}