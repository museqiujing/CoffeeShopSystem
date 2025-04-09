package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.entity.MaterialLog;
import com.coffeeshopsystem.coffeeshopsystem.entity.Material;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MaterialLogMapper;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MaterialMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.MaterialLogService;
import com.coffeeshopsystem.coffeeshopsystem.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialLogServiceImpl extends ServiceImpl<MaterialLogMapper, MaterialLog> implements MaterialLogService {

    private static final List<String> VALID_LOG_TYPES = Arrays.asList("IN", "OUT");

    private final MaterialMapper materialMapper;
    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logInbound(Integer materialId, Integer quantity, Integer operatorId, String remark) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限执行入库操作");
        }

        // 验证数量
        if (quantity <= 0) {
            throw new RuntimeException("入库数量必须大于0");
        }

        // 验证原材料是否存在
        Material material = materialMapper.selectById(materialId);
        if (material == null) {
            throw new RuntimeException("原材料不存在");
        }

        log.info("User {} logging inbound for material {}, quantity: {}",
                "museqiujing", material.getName(), quantity);

        // 创建入库日志
        MaterialLog log = new MaterialLog();
        log.setMaterialId(materialId);
        log.setType("IN");
        log.setQuantity(quantity);
        log.setOperatorId(operatorId);
        log.setCreatedAt(DateTimeUtil.parseDateTime("2025-04-08 14:54:16"));
        log.setRemark(remark);

        this.save(log);

        // 更新原材料库存
        material.setQuantity(material.getQuantity() + quantity);
        material.setUpdatedAt(DateTimeUtil.parseDateTime("2025-04-08 14:54:16"));
        materialMapper.updateById(material);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logOutbound(Integer materialId, Integer quantity, Integer operatorId, String remark) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限执行出库操作");
        }

        // 验证数量
        if (quantity <= 0) {
            throw new RuntimeException("出库数量必须大于0");
        }

        // 验证原材料是否存在
        Material material = materialMapper.selectById(materialId);
        if (material == null) {
            throw new RuntimeException("原材料不存在");
        }

        // 验证库存是否充足
        if (material.getQuantity() < quantity) {
            throw new RuntimeException("库存不足");
        }

        log.info("User {} logging outbound for material {}, quantity: {}",
                "museqiujing", material.getName(), quantity);

        // 创建出库日志
        MaterialLog log = new MaterialLog();
        log.setMaterialId(materialId);
        log.setType("OUT");
        log.setQuantity(quantity);
        log.setOperatorId(operatorId);
        log.setCreatedAt(DateTimeUtil.parseDateTime("2025-04-08 14:54:16"));
        log.setRemark(remark);

        this.save(log);

        // 更新原材料库存
        material.setQuantity(material.getQuantity() - quantity);
        material.setUpdatedAt(DateTimeUtil.parseDateTime("2025-04-08 14:54:16"));
        materialMapper.updateById(material);
    }

    @Override
    public List<MaterialLog> getMaterialLogs(Integer materialId) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看原材料日志");
        }

        return this.lambdaQuery()
                .eq(MaterialLog::getMaterialId, materialId)
                .orderByDesc(MaterialLog::getCreatedAt)
                .list();
    }

    @Override
    public Page<MaterialLog> getLogPage(Integer pageNum, Integer pageSize, String type) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看日志");
        }

        return this.lambdaQuery()
                .eq(type != null && VALID_LOG_TYPES.contains(type), MaterialLog::getType, type)
                .orderByDesc(MaterialLog::getCreatedAt)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    public List<MaterialLog> getLogsByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看日志");
        }

        return this.lambdaQuery()
                .between(MaterialLog::getCreatedAt, startTime, endTime)
                .orderByDesc(MaterialLog::getCreatedAt)
                .list();
    }

    @Override
    public List<MaterialLog> getLogsByOperator(Integer operatorId) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看日志");
        }

        return this.lambdaQuery()
                .eq(MaterialLog::getOperatorId, operatorId)
                .orderByDesc(MaterialLog::getCreatedAt)
                .list();
    }

    // 检查是否为员工或管理员
    private boolean isStaffOrAdmin() {
        Object role = request.getAttribute("role");
        return "STAFF".equals(role) || "ADMIN".equals(role);
    }
}