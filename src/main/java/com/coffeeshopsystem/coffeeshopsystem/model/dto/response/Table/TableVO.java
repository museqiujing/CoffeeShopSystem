package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Table;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class TableVO {
    private Integer id;
    private String tableNumber;
    private Integer seats;
    private String status;
    private LocalDateTime createdAt;

    // 扩展字段，用于前端展示
    private String statusText;    // 状态的中文描述
    private Boolean isAvailable;  // 是否可用（等同于 status == IDLE）
    private Integer currentOrderId;  // 当前订单ID（如果被占用）

    // 获取状态的中文描述
    public String getStatusText() {
        return "IDLE".equals(status) ? "空闲" : "占用中";
    }

    // 获取是否可用
    public Boolean getIsAvailable() {
        return "IDLE".equals(status);
    }
}