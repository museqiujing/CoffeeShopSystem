package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer categoryId;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private Integer stock;
    private Integer salesCount;
    private Integer status;    // 1-上架，0-下架
    private LocalDateTime updatedAt;
}