package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;

@Data
@Builder
public class MaterialStockStatVO {
    private Integer id;
    private String name;
    private String unit;
    private Integer currentStock;    // 当前库存
    private Integer minStock;        // 最小库存
    private Integer maxStock;        // 历史最高库存
    private Integer avgStock;        // 平均库存
    private BigDecimal totalInValue; // 入库总价值
    private BigDecimal totalOutValue;// 出库总价值
    private Integer turnoverRate;    // 周转率（百分比）
}