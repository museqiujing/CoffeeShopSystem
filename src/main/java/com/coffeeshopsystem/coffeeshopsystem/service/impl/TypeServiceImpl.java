package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.mapper.TypeMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.Type;
import com.coffeeshopsystem.coffeeshopsystem.service.TypeService;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
}