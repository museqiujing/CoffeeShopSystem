package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MaterialMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.Material;
import com.coffeeshopsystem.coffeeshopsystem.service.MaterialService;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {
}