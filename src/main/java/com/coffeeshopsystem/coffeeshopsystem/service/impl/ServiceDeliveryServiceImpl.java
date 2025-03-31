package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.mapper.ServiceDeliveryMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.ServiceDelivery;
import com.coffeeshopsystem.coffeeshopsystem.service.ServiceDeliveryService;
import org.springframework.stereotype.Service;

@Service
public class ServiceDeliveryServiceImpl extends ServiceImpl<ServiceDeliveryMapper, ServiceDelivery> implements ServiceDeliveryService {
}