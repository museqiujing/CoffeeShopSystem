package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MaterialQueryDTO {
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize = 10;

    private String keyword;           // 名称或供应商模糊搜索
    private String unit;             // 单位筛选
    private BigDecimal minPrice;     // 最低价格
    private BigDecimal maxPrice;     // 最高价格
    private Boolean lowStock;        // 是否只看库存预警
}