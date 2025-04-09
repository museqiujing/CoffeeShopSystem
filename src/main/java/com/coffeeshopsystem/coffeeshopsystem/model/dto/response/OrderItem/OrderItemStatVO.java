package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.OrderItem;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;

@Data
@Builder
public class OrderItemStatVO {
    private Integer menuId;
    private String menuName;
    private Integer totalQuantity;          // 总销售数量
    private BigDecimal totalAmount;         // 总销售金额
    private BigDecimal averagePrice;        // 平均售价
}