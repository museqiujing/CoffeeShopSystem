package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.mapper.OrderMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.Order;
import com.coffeeshopsystem.coffeeshopsystem.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}