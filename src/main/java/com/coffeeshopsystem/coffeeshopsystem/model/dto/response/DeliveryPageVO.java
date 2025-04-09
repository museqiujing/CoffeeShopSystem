package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class DeliveryPageVO {
    private Long total;
    private Long current;
    private Long size;
    private List<DeliveryVO> records;

    // 统计信息
    private Long pendingCount;     // 待分配数量
    private Long deliveringCount;  // 配送中数量
    private Double avgDuration;    // 平均配送时长（分钟）
}