package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Sales;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalesStatisticsCreateDTO {
    @NotNull(message = "统计日期不能为空")
    private LocalDate date;

    @NotNull(message = "销售总额不能为空")
    @DecimalMin(value = "0.0", message = "销售总额不能为负数")
    private BigDecimal totalSales;

    @NotNull(message = "订单数量不能为空")
    @Min(value = 0, message = "订单数量不能为负数")
    private Integer orderCount;
}