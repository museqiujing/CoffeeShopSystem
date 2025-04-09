package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Menu;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MenuUpdateDTO {
    @NotNull(message = "菜品ID不能为空")
    private Integer id;

    private Integer categoryId;

    private String name;

    @DecimalMin(value = "0.0", message = "价格不能小于0")
    private BigDecimal price;

    private String description;

    private String imageUrl;

    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;
}