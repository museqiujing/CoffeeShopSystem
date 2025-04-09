package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("sales_statistics")
public class SalesStatistics {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private LocalDate date;
    private BigDecimal totalSales;
    private Integer orderCount;
    private LocalDateTime createdAt;
}