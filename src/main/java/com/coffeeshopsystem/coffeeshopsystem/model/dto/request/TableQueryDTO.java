package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TableQueryDTO {
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize = 10;

    private String status;  // 可选：IDLE, OCCUPIED

    @Min(value = 0, message = "最小座位数不能小于0")
    private Integer minSeats;  // 最小座位数（可选）
}