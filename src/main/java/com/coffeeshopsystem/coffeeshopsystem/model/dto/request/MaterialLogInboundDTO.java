package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialLogInboundDTO {
    @NotNull(message = "原材料ID不能为空")
    private Integer materialId;

    @NotNull(message = "入库数量不能为空")
    @Min(value = 1, message = "入库数量必须大于0")
    private Integer quantity;

    @NotNull(message = "操作员ID不能为空")
    private Integer operatorId;

    private String remark;
}