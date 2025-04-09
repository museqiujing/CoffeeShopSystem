package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String orderNo;
    private Integer userId;
    private BigDecimal totalAmount;
    private String orderType;    // DINE_IN, TAKEOUT, DELIVERY
    private Integer tableId;
    private String status;       // PENDING, PAID, PREPARING, DELIVERING, COMPLETED, CANCELLED
    private String paymentStatus; // UNPAID, PAID, REFUNDED
    private String paymentMethod;
    private String deliveryAddress;
    private String deliveryPhone;
    private BigDecimal deliveryFee;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}