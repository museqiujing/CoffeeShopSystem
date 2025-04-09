package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Table;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TableCreateDTO {
    @NotBlank(message = "桌号不能为空")
    private String tableNumber;

    @NotNull(message = "座位数不能为空")
    @Min(value = 1, message = "座位数必须大于0")
    private Integer seats;
}