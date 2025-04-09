package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Menu;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MenuVO {
    private Integer id;
    private Integer categoryId;
    private String categoryName;  // 增加分类名称，便于前端显示
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private Integer stock;
    private Integer salesCount;
    private Integer status;      // 1-上架，0-下架
    private LocalDateTime updatedAt;

    // 额外的展示字段
    private Boolean available;   // 是否可购买（库存>0且状态为上架）

    public Boolean getAvailable() {
        return status == 1 && stock > 0;
    }
}