package com.coffeeshopsystem.coffeeshopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffeeshopsystem.coffeeshopsystem.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}