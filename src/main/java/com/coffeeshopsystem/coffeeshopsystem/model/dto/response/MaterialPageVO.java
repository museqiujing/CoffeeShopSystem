package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class MaterialPageVO {
    private Long total;
    private Long current;
    private Long size;
    private List<MaterialVO> records;

    // 统计信息
    private Integer totalMaterials;        // 原材料总数
    private Integer lowStockCount;         // 库存预警数量
    private BigDecimal totalStockValue;    // 库存总价值
    private Double averagePrice;           // 平均单价
}