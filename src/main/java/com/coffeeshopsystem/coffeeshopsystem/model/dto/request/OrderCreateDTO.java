package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreateDTO {
    @NotBlank(message = "订单类型不能为空")
    @Pattern(regexp = "^(DINE_IN|TAKEOUT|DELIVERY)$", message = "无效的订单类型")
    private String orderType;

    private Integer tableId;  // 堂食时必填

    // 配送信息（配送订单必填）
    private String deliveryAddress;
    private String deliveryPhone;
    private BigDecimal deliveryFee;

    private String remark;

    @NotEmpty(message = "订单项不能为空")
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Integer menuId;
        private Integer quantity;
        private BigDecimal price;
        private String remark;
    }

    // 用于验证订单数据
    public boolean validateDeliveryInfo() {
        if ("DELIVERY".equals(orderType)) {
            return deliveryAddress != null && !deliveryAddress.trim().isEmpty() &&
                    deliveryPhone != null && !deliveryPhone.trim().isEmpty() &&
                    deliveryFee != null;
        }
        return true;
    }

    public boolean validateTableInfo() {
        if ("DINE_IN".equals(orderType)) {
            return tableId != null;
        }
        return true;
    }
}