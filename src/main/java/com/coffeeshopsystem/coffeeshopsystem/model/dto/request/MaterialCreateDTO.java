package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MaterialCreateDTO {
    @NotBlank(message = "原材料名称不能为空")
    private String name;

    @NotBlank(message = "单位不能为空")
    private String unit;

    @NotBlank(message = "供应商不能为空")
    private String supplier;

    @NotNull(message = "最小库存不能为空")
    @Min(value = 0, message = "最小库存不能小于0")
    private Integer minStock;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.0", message = "价格不能小于0")
    private BigDecimal price;
}