package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Menu;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuStockUpdateDTO {
    @NotNull(message = "菜品ID不能为空")
    private Integer id;

    @NotNull(message = "库存数量不能为空")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;
}