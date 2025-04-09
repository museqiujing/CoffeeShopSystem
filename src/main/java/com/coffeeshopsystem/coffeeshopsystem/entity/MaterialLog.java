package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("material_logs")
public class MaterialLog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer materialId;
    private String type;         // IN, OUT
    private Integer quantity;
    private Integer operatorId;
    private LocalDateTime createdAt;
    private String remark;
}