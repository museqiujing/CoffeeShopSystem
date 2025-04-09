package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalesStatisticsQueryDTO {
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize = 10;

    private LocalDate startDate;
    private LocalDate endDate;

    // 扩展查询条件
    private BigDecimal minDailySales;    // 最小日销售额
    private BigDecimal maxDailySales;    // 最大日销售额
    private Integer minOrderCount;        // 最小订单数
    private Integer maxOrderCount;        // 最大订单数
}