package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.MaterialLog;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MaterialLogStatVO {
    private Integer materialId;
    private String materialName;
    private Integer inboundCount;     // 入库次数
    private Integer outboundCount;    // 出库次数
    private Integer totalInQuantity;  // 总入库数量
    private Integer totalOutQuantity; // 总出库数量
    private Integer currentStock;     // 当前库存
}