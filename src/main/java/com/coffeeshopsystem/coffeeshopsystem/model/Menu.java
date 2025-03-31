package com.coffeeshopsystem.coffeeshopsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("menu")
/*菜单*/
public class Menu {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
}