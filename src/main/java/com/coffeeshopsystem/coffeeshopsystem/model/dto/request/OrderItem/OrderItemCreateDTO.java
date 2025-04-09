package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.OrderItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemCreateDTO {
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;

    @NotNull(message = "菜品ID不能为空")
    private Integer menuId;

    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量必须大于0")
    private Integer quantity;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.0", message = "价格不能小于0")
    private BigDecimal price;
}