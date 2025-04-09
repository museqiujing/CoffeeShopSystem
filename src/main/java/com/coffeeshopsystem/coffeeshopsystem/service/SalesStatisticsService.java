package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.SalesStatistics;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SalesStatisticsService extends IService<SalesStatistics> {
    // 创建每日销售统计
    void createDailySalesStatistics(LocalDate date, BigDecimal totalSales, Integer orderCount);

    // 获取指定日期的销售统计
    SalesStatistics getDailySalesStatistics(LocalDate date);

    // 获取日期范围内的销售统计
    List<SalesStatistics> getSalesStatisticsByDateRange(LocalDate startDate, LocalDate endDate);

    // 分页查询销售统计
    Page<SalesStatistics> getSalesStatisticsPage(Integer pageNum, Integer pageSize);

    // 统计日期范围内的总销售额
    BigDecimal calculateTotalSales(LocalDate startDate, LocalDate endDate);

    // 统计日期范围内的总订单数
    Integer calculateTotalOrders(LocalDate startDate, LocalDate endDate);
}