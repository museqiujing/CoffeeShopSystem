package com.coffeeshopsystem.coffeeshopsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("service_delivery")
/*人工服务配送管理*/
public class ServiceDelivery {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String serviceName;
    private String description;
    private Double price;
}