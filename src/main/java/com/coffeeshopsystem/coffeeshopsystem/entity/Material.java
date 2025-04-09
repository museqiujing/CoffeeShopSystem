package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("materials")
public class Material {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer quantity;
    private String unit;
    private String supplier;
    private Integer minStock;
    private BigDecimal price;
    private LocalDateTime updatedAt;
}