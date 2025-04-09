package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDetailVO {
    private Integer id;
    private String orderNo;
    private Integer userId;
    private BigDecimal totalAmount;
    private String orderType;    // DINE_IN, TAKEOUT, DELIVERY
    private Integer tableId;
    private String status;       // PENDING, PAID, PREPARING, DELIVERING, COMPLETED, CANCELLED
    private String paymentStatus; // UNPAID, PAID, REFUNDED
    private String paymentMethod;

    // 配送信息
    private String deliveryAddress;
    private String deliveryPhone;
    private BigDecimal deliveryFee;

    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 订单项列表
    private List<OrderItemVO> items;

    @Data
    @Builder
    public static class OrderItemVO {
        private Integer id;
        private Integer menuId;
        private String menuName;    // 增加菜品名称，便于前端显示
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal; // 增加小计金额
        private String remark;
    }

    // 用户信息（可选，根据需要返回）
    private UserInfoVO user;
}