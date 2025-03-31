package com.coffeeshopsystem.coffeeshopsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("orders")
/*订单*/
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private int userId;
    private int menuId;
    private int quantity;
    private double totalPrice;
    private String status;
    private Timestamp orderTime;
    private Timestamp deliveryTime;
}