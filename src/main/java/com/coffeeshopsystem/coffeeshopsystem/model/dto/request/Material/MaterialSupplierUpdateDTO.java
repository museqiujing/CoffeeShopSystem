package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialSupplierUpdateDTO {
    @NotNull(message = "原材料ID不能为空")
    private Integer id;

    @NotBlank(message = "供应商不能为空")
    private String supplier;
}