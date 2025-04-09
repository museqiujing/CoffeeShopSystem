package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Delivery;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class DeliveryVO {
    private Integer id;
    private Integer orderId;
    private Integer delivererId;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime updatedAt;

    // 扩展字段
    private String statusText;          // 状态中文描述
    private String delivererName;       // 配送员姓名
    private String orderInfo;           // 订单信息摘要
    private Long deliveryDuration;      // 配送时长（分钟）

    // 获取状态的中文描述
    public String getStatusText() {
        return switch (status) {
            case "PENDING" -> "待分配";
            case "PICKING" -> "取餐中";
            case "DELIVERING" -> "配送中";
            case "COMPLETED" -> "已完成";
            case "CANCELLED" -> "已取消";
            default -> status;
        };
    }
}