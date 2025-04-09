package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Table;

import lombok.Data;
import java.util.List;

@Data
public class TablePageVO {
    private Long total;
    private Long current;
    private Long size;
    private List<TableVO> records;

    // 扩展字段，用于统计
    private Long idleCount;     // 空闲餐桌数量
    private Long occupiedCount; // 占用餐桌数量
    private Integer totalSeats; // 总座位数
}