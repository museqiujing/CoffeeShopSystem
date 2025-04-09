package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Sales;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class DailySalesVO {
    private Integer id;
    private LocalDate date;
    private BigDecimal totalSales;
    private Integer orderCount;
    private LocalDateTime createdAt;

    // 扩展字段
    private BigDecimal averageOrderAmount;    // 平均订单金额
    private String dayOfWeek;                 // 星期几
    private Boolean isWeekend;                // 是否周末
    private String performanceLevel;          // 销售表现等级

    // 获取星期几的中文描述
    public String getDayOfWeek() {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        return weekDays[date.getDayOfWeek().getValue() % 7];
    }

    // 获取销售表现等级
    public String getPerformanceLevel() {
        if (orderCount == 0) return "无销售";
        Double avgAmount = totalSales.doubleValue() / orderCount;
        if (avgAmount >= 200) return "优秀";
        if (avgAmount >= 100) return "良好";
        return "一般";
    }
}