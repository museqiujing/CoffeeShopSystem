package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("tables")
public class Table {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String tableNumber;
    private Integer seats;
    private String status;    // IDLE, OCCUPIED
    private LocalDateTime createdAt;
}