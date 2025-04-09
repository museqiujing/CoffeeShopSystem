package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TableStatusUpdateDTO {
    @NotNull(message = "餐桌ID不能为空")
    private Integer id;

    @NotBlank(message = "状态不能为空")
    @Pattern(regexp = "^(IDLE|OCCUPIED)$", message = "无效的餐桌状态")
    private String status;
}