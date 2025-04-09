package com.coffeeshopsystem.coffeeshopsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("delivery")
public class Delivery {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer delivererId;
    private String status;       // PENDING, PICKING, DELIVERING, COMPLETED, CANCELLED
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime updatedAt;
}