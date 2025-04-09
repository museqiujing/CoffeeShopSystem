package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coffeeshopsystem.coffeeshopsystem.entity.Delivery;
import com.coffeeshopsystem.coffeeshopsystem.mapper.DeliveryMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.DeliveryService;
import com.coffeeshopsystem.coffeeshopsystem.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl extends ServiceImpl<DeliveryMapper, Delivery> implements DeliveryService {

    private static final List<String> VALID_STATUS = Arrays.asList(
            "PENDING",      // 待分配
            "PICKING",      // 取餐中
            "DELIVERING",   // 配送中
            "COMPLETED",    // 已完成
            "CANCELLED"     // 已取消
    );

    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDelivery(Delivery delivery) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限创建配送订单");
        }

        log.info("User {} creating delivery for order {}",
                getCurrentUsername(), delivery.getOrderId());

        // 设置初始状态和时间
        delivery.setStatus("PENDING");
        delivery.setStartTime(DateTimeUtil.getCurrentDateTime());
        delivery.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.save(delivery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignDeliverer(Integer id, Integer delivererId) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限分配配送员");
        }

        Delivery delivery = this.getById(id);
        if (delivery == null) {
            throw new RuntimeException("配送订单不存在");
        }

        if (!("PENDING".equals(delivery.getStatus()))) {
            throw new RuntimeException("当前状态不可分配配送员");
        }

        log.info("User {} assigning deliverer {} to delivery {}",
                getCurrentUsername(), delivererId, id);

        delivery.setDelivererId(delivererId);
        delivery.setStatus("PICKING");
        delivery.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.updateById(delivery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        // 权限检查
        if (!isAuthorizedToModifyDelivery(id)) {
            throw new RuntimeException("无权限更新配送状态");
        }

        if (!VALID_STATUS.contains(status)) {
            throw new RuntimeException("无效的配送状态");
        }

        Delivery delivery = this.getById(id);
        if (delivery == null) {
            throw new RuntimeException("配送订单不存在");
        }

        // 状态流转检查
        validateStatusTransition(delivery.getStatus(), status);

        log.info("User {} updating delivery {} status from {} to {}",
                getCurrentUsername(), id, delivery.getStatus(), status);

        delivery.setStatus(status);
        delivery.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.updateById(delivery);
    }

    @Override
    public List<Delivery> getCurrentDeliveries(Integer delivererId) {
        // 权限检查
        if (!isAuthorizedToViewDeliveries(delivererId)) {
            throw new RuntimeException("无权限查看配送订单");
        }

        return this.lambdaQuery()
                .eq(Delivery::getDelivererId, delivererId)
                .in(Delivery::getStatus, Arrays.asList("PICKING", "DELIVERING"))
                .orderByAsc(Delivery::getStartTime)
                .list();
    }

    @Override
    public Page<Delivery> getDeliveryPage(Integer pageNum, Integer pageSize, String status) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看配送订单列表");
        }

        if (status != null && !VALID_STATUS.contains(status)) {
            throw new RuntimeException("无效的配送状态");
        }

        return this.lambdaQuery()
                .eq(status != null, Delivery::getStatus, status)
                .orderByDesc(Delivery::getUpdatedAt)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    public List<Delivery> getDeliveriesByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看配送订单列表");
        }

        return this.lambdaQuery()
                .between(Delivery::getStartTime, startTime, endTime)
                .orderByDesc(Delivery::getStartTime)
                .list();
    }

    @Override
    public List<Delivery> getPendingDeliveries() {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看待分配配送订单");
        }

        return this.lambdaQuery()
                .eq(Delivery::getStatus, "PENDING")
                .orderByAsc(Delivery::getStartTime)
                .list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeDelivery(Integer id) {
        // 权限检查
        if (!isAuthorizedToModifyDelivery(id)) {
            throw new RuntimeException("无权限完成配送订单");
        }

        Delivery delivery = this.getById(id);
        if (delivery == null) {
            throw new RuntimeException("配送订单不存在");
        }

        if (!("DELIVERING".equals(delivery.getStatus()))) {
            throw new RuntimeException("只有配送中的订单可以完成配送");
        }

        log.info("User {} completing delivery {}", getCurrentUsername(), id);

        delivery.setStatus("COMPLETED");
        delivery.setEndTime(DateTimeUtil.getCurrentDateTime());
        delivery.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.updateById(delivery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelDelivery(Integer id, String reason) {
        // 权限检查
        if (!isAuthorizedToModifyDelivery(id)) {
            throw new RuntimeException("无权限取消配送订单");
        }

        Delivery delivery = this.getById(id);
        if (delivery == null) {
            throw new RuntimeException("配送订单不存在");
        }

        if ("COMPLETED".equals(delivery.getStatus())) {
            throw new RuntimeException("已完成的订单不能取消");
        }

        log.info("User {} cancelling delivery {}, reason: {}",
                getCurrentUsername(), id, reason);

        delivery.setStatus("CANCELLED");
        delivery.setEndTime(DateTimeUtil.getCurrentDateTime());
        delivery.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.updateById(delivery);
    }

    // 验证状态流转是否合法
    private void validateStatusTransition(String currentStatus, String newStatus) {
        switch (currentStatus) {
            case "PENDING":
                if (!Arrays.asList("PICKING", "CANCELLED").contains(newStatus)) {
                    throw new RuntimeException("待分配状态只能变更为取餐中或已取消");
                }
                break;
            case "PICKING":
                if (!Arrays.asList("DELIVERING", "CANCELLED").contains(newStatus)) {
                    throw new RuntimeException("取餐中状态只能变更为配送中或已取消");
                }
                break;
            case "DELIVERING":
                if (!Arrays.asList("COMPLETED", "CANCELLED").contains(newStatus)) {
                    throw new RuntimeException("配送中状态只能变更为已完成或已取消");
                }
                break;
            case "COMPLETED":
            case "CANCELLED":
                throw new RuntimeException("已完成或已取消的订单不能变更状态");
            default:
                throw new RuntimeException("无效的当前状态");
        }
    }

    // 检查是否有权限修改配送订单
    private boolean isAuthorizedToModifyDelivery(Integer deliveryId) {
        Object userId = request.getAttribute("userId");
        Object role = request.getAttribute("role");

        if ("ADMIN".equals(role) || "STAFF".equals(role)) {
            return true;
        }

        Delivery delivery = this.getById(deliveryId);
        return delivery != null && userId != null &&
                userId.equals(delivery.getDelivererId());
    }

    // 检查是否有权限查看配送订单
    private boolean isAuthorizedToViewDeliveries(Integer delivererId) {
        Object userId = request.getAttribute("userId");
        Object role = request.getAttribute("role");

        return "ADMIN".equals(role) || "STAFF".equals(role) ||
                (userId != null && userId.equals(delivererId));
    }

    // 检查是否为员工或管理员
    private boolean isStaffOrAdmin() {
        Object role = request.getAttribute("role");
        return "STAFF".equals(role) || "ADMIN".equals(role);
    }

    // 获取当前用户名
    private String getCurrentUsername() {
        Object username = request.getAttribute("username");
        return username != null ? username.toString() : "unknown";
    }
}