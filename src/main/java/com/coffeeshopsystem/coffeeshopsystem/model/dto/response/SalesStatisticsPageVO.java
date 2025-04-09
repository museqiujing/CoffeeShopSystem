package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SalesStatisticsPageVO {
    private Long total;
    private Long current;
    private Long size;
    private List<DailySalesVO> records;

    // 统计信息
    private BigDecimal periodTotalSales;      // 期间总销售额
    private Integer periodTotalOrders;        // 期间总订单数
    private BigDecimal avgDailySales;         // 日均销售额
    private Double avgOrderAmount;            // 平均订单金额
    private Integer maxOrdersDay;             // 最高订单日
    private BigDecimal maxSalesDay;          // 最高销售日
}