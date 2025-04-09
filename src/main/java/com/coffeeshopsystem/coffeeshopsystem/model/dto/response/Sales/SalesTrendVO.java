package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Sales;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SalesTrendVO {
    private BigDecimal totalSales;            // 总销售额
    private Integer totalOrders;              // 总订单数
    private BigDecimal avgDailySales;         // 日均销售额
    private Double avgOrderAmount;            // 平均订单金额
    private Double orderGrowthRate;           // 订单增长率
    private Double salesGrowthRate;           // 销售额增长率

    // 趋势数据
    private List<BigDecimal> dailySalesTrend;     // 日销售额趋势
    private List<Integer> dailyOrdersTrend;       // 日订单数趋势
    private Map<String, Double> weekdayAnalysis;   // 工作日分析
    private List<String> topPerformanceDays;      // 表现最佳的日期
}