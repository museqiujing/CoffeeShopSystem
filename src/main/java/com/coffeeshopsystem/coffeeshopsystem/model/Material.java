package com.coffeeshopsystem.coffeeshopsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("materials")
/*原材料管理*/
public class Material {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Double price;
    private Integer stock;
}