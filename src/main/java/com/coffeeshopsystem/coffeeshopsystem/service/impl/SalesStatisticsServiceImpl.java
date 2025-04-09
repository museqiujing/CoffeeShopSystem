package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.entity.SalesStatistics;
import com.coffeeshopsystem.coffeeshopsystem.mapper.SalesStatisticsMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.SalesStatisticsService;
import com.coffeeshopsystem.coffeeshopsystem.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesStatisticsServiceImpl extends ServiceImpl<SalesStatisticsMapper, SalesStatistics> implements SalesStatisticsService {

    private static final int PRICE_SCALE = 2;
    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDailySalesStatistics(LocalDate date, BigDecimal totalSales, Integer orderCount) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限创建销售统计");
        }

        // 检查该日期是否已有统计
        SalesStatistics existingStats = this.lambdaQuery()
                .eq(SalesStatistics::getDate, date)
                .one();

        if (existingStats != null) {
            throw new RuntimeException("该日期的销售统计已存在");
        }

        // 参数验证
        if (totalSales.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("销售额不能为负数");
        }
        if (orderCount < 0) {
            throw new RuntimeException("订单数不能为负数");
        }

        log.info("User {} creating sales statistics for date {}, total sales: {}, order count: {}",
                "museqiujing", date, totalSales, orderCount);

        // 创建新的销售统计
        SalesStatistics statistics = new SalesStatistics();
        statistics.setDate(date);
        statistics.setTotalSales(totalSales.setScale(PRICE_SCALE, RoundingMode.HALF_UP));
        statistics.setOrderCount(orderCount);
        statistics.setCreatedAt(DateTimeUtil.parseDateTime("2025-04-08 14:55:47"));

        this.save(statistics);
    }

    @Override
    public SalesStatistics getDailySalesStatistics(LocalDate date) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看销售统计");
        }

        SalesStatistics stats = this.lambdaQuery()
                .eq(SalesStatistics::getDate, date)
                .one();

        if (stats != null) {
            log.info("User {} retrieving sales statistics for date {}",
                    "museqiujing", date);
        }

        return stats;
    }

    @Override
    public List<SalesStatistics> getSalesStatisticsByDateRange(LocalDate startDate, LocalDate endDate) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看销售统计");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("开始日期不能晚于结束日期");
        }

        log.info("User {} retrieving sales statistics from {} to {}",
                "museqiujing", startDate, endDate);

        return this.lambdaQuery()
                .between(SalesStatistics::getDate, startDate, endDate)
                .orderByAsc(SalesStatistics::getDate)
                .list();
    }

    @Override
    public Page<SalesStatistics> getSalesStatisticsPage(Integer pageNum, Integer pageSize) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看销售统计");
        }

        log.info("User {} retrieving sales statistics page {} size {}",
                "museqiujing", pageNum, pageSize);

        return this.lambdaQuery()
                .orderByDesc(SalesStatistics::getDate)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    public BigDecimal calculateTotalSales(LocalDate startDate, LocalDate endDate) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看销售统计");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("开始日期不能晚于结束日期");
        }

        log.info("User {} calculating total sales from {} to {}",
                "museqiujing", startDate, endDate);

        BigDecimal totalSales = this.lambdaQuery()
                .between(SalesStatistics::getDate, startDate, endDate)
                .list()
                .stream()
                .map(SalesStatistics::getTotalSales)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSales.setScale(PRICE_SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public Integer calculateTotalOrders(LocalDate startDate, LocalDate endDate) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看销售统计");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("开始日期不能晚于结束日期");
        }

        log.info("User {} calculating total orders from {} to {}",
                "museqiujing", startDate, endDate);

        return this.lambdaQuery()
                .between(SalesStatistics::getDate, startDate, endDate)
                .list()
                .stream()
                .mapToInt(SalesStatistics::getOrderCount)
                .sum();
    }

    // 检查是否为员工或管理员
    private boolean isStaffOrAdmin() {
        Object role = request.getAttribute("role");
        return "STAFF".equals(role) || "ADMIN".equals(role);
    }
}