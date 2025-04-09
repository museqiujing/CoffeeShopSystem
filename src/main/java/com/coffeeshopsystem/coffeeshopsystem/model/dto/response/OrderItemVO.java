package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;

@Data
@Builder
public class OrderItemVO {
    private Integer id;
    private Integer orderId;
    private Integer menuId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    // 扩展字段，便于前端显示
    private String menuName;        // 菜品名称
    private String menuImage;       // 菜品图片
    private String categoryName;    // 菜品分类名称
}