package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.MaterialLog;

import lombok.Data;
import java.util.List;

@Data
public class MaterialLogPageVO {
    private Long total;
    private Long current;
    private Long size;
    private List<MaterialLogVO> records;

    // 统计信息
    private Integer totalInbound;     // 总入库次数
    private Integer totalOutbound;    // 总出库次数
    private Integer totalInQuantity;  // 总入库数量
    private Integer totalOutQuantity; // 总出库数量
}