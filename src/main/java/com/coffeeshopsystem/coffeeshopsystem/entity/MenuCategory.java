package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("menu_categories")
public class MenuCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Integer sortOrder;
    private String categoryType;  // DESSERT, COFFEE, OTHER
}