package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.entity.Order;
import com.coffeeshopsystem.coffeeshopsystem.entity.OrderItem;
import com.coffeeshopsystem.coffeeshopsystem.mapper.OrderMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.OrderService;
import com.coffeeshopsystem.coffeeshopsystem.service.OrderItemService;
import com.coffeeshopsystem.coffeeshopsystem.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private static final List<String> VALID_ORDER_TYPES = Arrays.asList("DINE_IN", "TAKEOUT", "DELIVERY");
    private static final List<String> VALID_ORDER_STATUS = Arrays.asList("PENDING", "PAID", "PREPARING", "DELIVERING", "COMPLETED", "CANCELLED");
    private static final List<String> VALID_PAYMENT_STATUS = Arrays.asList("UNPAID", "PAID", "REFUNDED");
    private static final int PRICE_SCALE = 2;

    private final OrderItemService orderItemService;
    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Order order, List<OrderItem> items) {
        // 验证订单类型
        if (!VALID_ORDER_TYPES.contains(order.getOrderType())) {
            throw new RuntimeException("无效的订单类型");
        }

        // 设置当前用户ID
        Object userId = request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("未登录");
        }
        order.setUserId((Integer) userId);

        // 设置订单基本信息
        order.setOrderNo(generateOrderNo());
        order.setStatus("PENDING");
        order.setPaymentStatus("UNPAID");
        order.setCreatedAt(DateTimeUtil.getCurrentDateTime());
        order.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        // 配送订单特殊处理
        if ("DELIVERY".equals(order.getOrderType())) {
            if (!validateDeliveryOrder(order)) {
                throw new RuntimeException("配送订单信息不完整");
            }
        } else {
            clearDeliveryInfo(order);
        }

        // 计算订单总金额
        BigDecimal totalAmount = calculateTotalAmount(items, order.getDeliveryFee());
        order.setTotalAmount(totalAmount.setScale(PRICE_SCALE, RoundingMode.HALF_UP));

        // 记录操作日志
        log.info("User {} creating order: type={}, amount={}",
                getCurrentUsername(), order.getOrderType(), order.getTotalAmount());

        // 保存订单
        this.save(order);

        // 保存订单项
        orderItemService.createOrderItems(order.getId(), items);

        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Integer id, String status) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限执行此操作");
        }

        if (!VALID_ORDER_STATUS.contains(status)) {
            throw new RuntimeException("无效的订单状态");
        }

        Order order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        log.info("User {} updating order {} status from {} to {}",
                getCurrentUsername(), id, order.getStatus(), status);

        order.setStatus(status);
        order.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePaymentStatus(Integer id, String paymentStatus, String paymentMethod) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限执行此操作");
        }

        if (!VALID_PAYMENT_STATUS.contains(paymentStatus)) {
            throw new RuntimeException("无效的支付状态");
        }

        Order order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        log.info("User {} updating order {} payment status from {} to {}",
                getCurrentUsername(), id, order.getPaymentStatus(), paymentStatus);

        order.setPaymentStatus(paymentStatus);
        order.setPaymentMethod(paymentMethod);
        order.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        if ("PAID".equals(paymentStatus)) {
            order.setStatus("PAID");
        }

        this.updateById(order);
    }

    @Override
    public List<Order> getUserOrders(Integer userId) {
        // 权限检查
        if (!isAuthorizedToViewOrders(userId)) {
            throw new RuntimeException("无权限查看此用户订单");
        }

        return this.lambdaQuery()
                .eq(Order::getUserId, userId)
                .orderByDesc(Order::getCreatedAt)
                .list();
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看订单列表");
        }

        if (!VALID_ORDER_STATUS.contains(status)) {
            throw new RuntimeException("无效的订单状态");
        }

        return this.lambdaQuery()
                .eq(Order::getStatus, status)
                .orderByDesc(Order::getCreatedAt)
                .list();
    }

    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看订单列表");
        }

        return this.lambdaQuery()
                .between(Order::getCreatedAt, startTime, endTime)
                .orderByDesc(Order::getCreatedAt)
                .list();
    }

    @Override
    public List<Order> getTableOrders(Integer tableId) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看餐桌订单");
        }

        return this.lambdaQuery()
                .eq(Order::getTableId, tableId)
                .orderByDesc(Order::getCreatedAt)
                .list();
    }

    @Override
    public Page<Order> getOrderPage(Integer pageNum, Integer pageSize, String orderType, String status) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限查看订单列表");
        }

        return this.lambdaQuery()
                .eq(StringUtils.hasText(orderType) && VALID_ORDER_TYPES.contains(orderType),
                        Order::getOrderType, orderType)
                .eq(StringUtils.hasText(status) && VALID_ORDER_STATUS.contains(status),
                        Order::getStatus, status)
                .orderByDesc(Order::getCreatedAt)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Integer id, String reason) {
        Order order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 权限检查
        if (!isAuthorizedToModifyOrder(order)) {
            throw new RuntimeException("无权限取消此订单");
        }

        // 检查订单状态
        if (!canCancel(order.getStatus())) {
            throw new RuntimeException("当前订单状态不可取消");
        }

        log.info("User {} cancelling order {}, reason: {}",
                getCurrentUsername(), id, reason);

        order.setStatus("CANCELLED");
        order.setRemark(reason);
        order.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.updateById(order);
    }

    // 计算订单总金额
    private BigDecimal calculateTotalAmount(List<OrderItem> items, BigDecimal deliveryFee) {
        BigDecimal totalAmount = items.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalAmount.add(deliveryFee != null ? deliveryFee : BigDecimal.ZERO)
                .setScale(PRICE_SCALE, RoundingMode.HALF_UP);
    }

    // 生成订单编号
    private String generateOrderNo() {
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return timestamp + random;
    }

    // 验证配送订单信息
    private boolean validateDeliveryOrder(Order order) {
        return StringUtils.hasText(order.getDeliveryAddress()) &&
                StringUtils.hasText(order.getDeliveryPhone()) &&
                (order.getDeliveryFee() != null);
    }

    // 清除配送信息
    private void clearDeliveryInfo(Order order) {
        order.setDeliveryAddress(null);
        order.setDeliveryPhone(null);
        order.setDeliveryFee(BigDecimal.ZERO);
    }

    // 检查订单是否可以取消
    private boolean canCancel(String status) {
        return "PENDING".equals(status) || "PAID".equals(status);
    }

    // 检查是否有权限查看订单
    private boolean isAuthorizedToViewOrders(Integer targetUserId) {
        Object userId = request.getAttribute("userId");
        Object role = request.getAttribute("role");

        return "ADMIN".equals(role) || "STAFF".equals(role) ||
                (userId != null && userId.equals(targetUserId));
    }

    // 检查是否有权限修改订单
    private boolean isAuthorizedToModifyOrder(Order order) {
        Object userId = request.getAttribute("userId");
        Object role = request.getAttribute("role");

        return "ADMIN".equals(role) || "STAFF".equals(role) ||
                (userId != null && userId.equals(order.getUserId()));
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