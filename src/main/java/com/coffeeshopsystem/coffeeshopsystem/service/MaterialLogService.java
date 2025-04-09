package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.MaterialLog;
import java.time.LocalDateTime;
import java.util.List;

public interface MaterialLogService extends IService<MaterialLog> {
    // 记录入库日志
    void logInbound(Integer materialId, Integer quantity, Integer operatorId, String remark);

    // 记录出库日志
    void logOutbound(Integer materialId, Integer quantity, Integer operatorId, String remark);

    // 获取指定原材料的日志记录
    List<MaterialLog> getMaterialLogs(Integer materialId);

    // 分页查询日志
    Page<MaterialLog> getLogPage(Integer pageNum, Integer pageSize, String type);

    // 获取指定时间范围内的日志
    List<MaterialLog> getLogsByDateRange(LocalDateTime startTime, LocalDateTime endTime);

    // 获取指定操作员的日志记录
    List<MaterialLog> getLogsByOperator(Integer operatorId);
}