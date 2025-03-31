package com.coffeeshopsystem.coffeeshopsystem.controller;

import com.coffeeshopsystem.coffeeshopsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
/*报表统计功能*/
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/sales")
    public ResponseEntity<Map<String, Object>> getSalesReport() {
        Map<String, Object> report = reportService.getSalesReport();
        return ResponseEntity.ok(report);
    }
}