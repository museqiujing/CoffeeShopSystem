package com.coffeeshopsystem.coffeeshopsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("types")
/*类型管理*/
public class Type {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
}