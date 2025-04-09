package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Material;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MaterialVO {
    private Integer id;
    private String name;
    private Integer quantity;
    private String unit;
    private String supplier;
    private Integer minStock;
    private BigDecimal price;
    private LocalDateTime updatedAt;

    // 扩展字段
    private Boolean isLowStock;          // 是否库存预警
    private Integer stockPercentage;     // 库存占最小库存的百分比
    private BigDecimal totalValue;       // 库存总价值
    private String lastOperator;         // 最后操作人
    private String stockStatus;          // 库存状态描述

    // 获取库存状态描述
    public String getStockStatus() {
        if (quantity <= 0) {
            return "无库存";
        }
        if (quantity <= minStock) {
            return "库存不足";
        }
        if (quantity <= minStock * 2) {
            return "库存偏低";
        }
        return "库存充足";
    }
}