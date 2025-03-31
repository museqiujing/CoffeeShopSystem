package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.coffeeshopsystem.coffeeshopsystem.mapper.OrderMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.Order;
import com.coffeeshopsystem.coffeeshopsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map<String, Object> getSalesReport() {
        Map<String, Object> report = new HashMap<>();
        // 添加统计逻辑，例如总销售额、订单数量等
        double totalSales = orderMapper.selectList(null).stream().mapToDouble(Order::getTotalPrice).sum();
        int totalOrders = orderMapper.selectCount(null).intValue();
        report.put("totalSales", totalSales);
        report.put("totalOrders", totalOrders);
        return report;
    }
}