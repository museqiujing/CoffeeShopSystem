package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Delivery;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryCreateDTO {
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;
}