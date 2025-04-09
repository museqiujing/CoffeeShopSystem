package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.entity.Order;
import com.coffeeshopsystem.coffeeshopsystem.entity.OrderItem;
import com.coffeeshopsystem.coffeeshopsystem.mapper.OrderItemMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.OrderItemService;
import com.coffeeshopsystem.coffeeshopsystem.service.OrderService;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    private static final int PRICE_SCALE = 2;

    private final OrderService orderService;
    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<OrderItem> createOrderItem(Integer orderId, Integer menuId, Integer quantity, BigDecimal price) {
        try {
            // 验证订单是否存在并检查权限
            Order order = orderService.getById(orderId);
            if (order == null) {
                return Result.error("订单不存在");
            }

            // 验证订单所有权
            if (!isAuthorizedToModifyOrder(order)) {
                return Result.error("无权限修改此订单");
            }

            // 验证订单状态
            if (!"PENDING".equals(order.getStatus())) {
                return Result.error("只能为待处理订单添加商品");
            }

            // 验证数量和价格
            if (quantity <= 0) {
                return Result.error("商品数量必须大于0");
            }
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                return Result.error("商品价格不能为负数");
            }

            log.info("User {} creating order item for order {}, menu item {}",
                    "museqiujing", orderId, menuId);

            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setMenuId(menuId);
            item.setQuantity(quantity);
            item.setPrice(price.setScale(PRICE_SCALE, RoundingMode.HALF_UP));
            // 计算小计金额
            item.setSubtotal(price.multiply(new BigDecimal(quantity))
                    .setScale(PRICE_SCALE, RoundingMode.HALF_UP));

            this.save(item);
            return Result.ok(item);
        } catch (Exception e) {
            log.error("Failed to create order item", e);
            return Result.error(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<List<OrderItem>> createOrderItems(Integer orderId, List<OrderItem> items) {
        try {
            // 验证订单是否存在并检查权限
            Order order = orderService.getById(orderId);
            if (order == null) {
                return Result.error("订单不存在");
            }

            // 验证订单所有权
            if (!isAuthorizedToModifyOrder(order)) {
                return Result.error("无权限修改此订单");
            }

            // 验证订单状态
            if (!"PENDING".equals(order.getStatus())) {
                return Result.error("只能为待处理订单添加商品");
            }

            log.info("User {} creating {} order items for order {}",
                    "museqiujing", items.size(), orderId);

            // 批量保存订单项
            for (OrderItem item : items) {
                item.setOrderId(orderId);
                item.setPrice(item.getPrice().setScale(PRICE_SCALE, RoundingMode.HALF_UP));
                // 计算小计金额
                item.setSubtotal(item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        .setScale(PRICE_SCALE, RoundingMode.HALF_UP));
                this.save(item);
            }

            return Result.ok(items);
        } catch (Exception e) {
            log.error("Failed to create order items", e);
            return Result.error(e.getMessage());
        }
    }

    @Override
    public Result<List<OrderItem>> getOrderItems(Integer orderId) {
        try {
            // 验证订单是否存在并检查权限
            Order order = orderService.getById(orderId);
            if (order == null) {
                return Result.error("订单不存在");
            }

            // 验证查看权限
            if (!isAuthorizedToViewOrder(order)) {
                return Result.error("无权限查看此订单");
            }

            log.info("User {} retrieving order items for order {}",
                    "museqiujing", orderId);

            List<OrderItem> items = this.lambdaQuery()
                    .eq(OrderItem::getOrderId, orderId)
                    .list();

            return Result.ok(items);
        } catch (Exception e) {
            log.error("Failed to get order items", e);
            return Result.error(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateQuantity(Integer id, Integer quantity) {
        try {
            if (quantity <= 0) {
                return Result.error("商品数量必须大于0");
            }

            OrderItem item = this.getById(id);
            if (item == null) {
                return Result.error("订单项不存在");
            }

            // 验证订单所有权
            Order order = orderService.getById(item.getOrderId());
            if (order == null) {
                return Result.error("订单不存在");
            }

            if (!isAuthorizedToModifyOrder(order)) {
                return Result.error("无权限修改此订单");
            }

            // 验证订单状态
            if (!"PENDING".equals(order.getStatus())) {
                return Result.error("只能修改待处理订单的商品数量");
            }

            log.info("User {} updating quantity for order item {} from {} to {}",
                    "museqiujing", id, item.getQuantity(), quantity);

            item.setQuantity(quantity);
            // 更新小计金额
            item.setSubtotal(item.getPrice().multiply(new BigDecimal(quantity))
                    .setScale(PRICE_SCALE, RoundingMode.HALF_UP));
            this.updateById(item);

            return Result.ok();
        } catch (Exception e) {
            log.error("Failed to update order item quantity", e);
            return Result.error(e.getMessage());
        }
    }

    @Override
    public Result<BigDecimal> calculateSubtotal(Integer quantity, BigDecimal price) {
        try {
            if (quantity <= 0) {
                return Result.error("数量必须大于0");
            }
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                return Result.error("价格不能为负数");
            }

            BigDecimal subtotal = price.multiply(new BigDecimal(quantity))
                    .setScale(PRICE_SCALE, RoundingMode.HALF_UP);

            return Result.ok(subtotal);
        } catch (Exception e) {
            log.error("Failed to calculate subtotal", e);
            return Result.error(e.getMessage());
        }
    }

    @Override
    public Result<Integer> getTotalQuantityByMenuId(Integer menuId) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error("无权限查看统计数据");
            }

            log.info("User {} retrieving total quantity for menu item {}",
                    "museqiujing", menuId);

            Integer totalQuantity = this.lambdaQuery()
                    .eq(OrderItem::getMenuId, menuId)
                    .list()
                    .stream()
                    .mapToInt(OrderItem::getQuantity)
                    .sum();

            return Result.ok(totalQuantity);
        } catch (Exception e) {
            log.error("Failed to get total quantity for menu item", e);
            return Result.error(e.getMessage());
        }
    }

    // 检查是否有权限修改订单
    private boolean isAuthorizedToModifyOrder(Order order) {
        Object userId = request.getAttribute("userId");
        Object role = request.getAttribute("role");

        return "ADMIN".equals(role) || "STAFF".equals(role) ||
                (userId != null && userId.equals(order.getUserId()));
    }

    // 检查是否有权限查看订单
    private boolean isAuthorizedToViewOrder(Order order) {
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
}