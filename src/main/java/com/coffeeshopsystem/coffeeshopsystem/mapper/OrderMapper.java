package com.coffeeshopsystem.coffeeshopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffeeshopsystem.coffeeshopsystem.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}