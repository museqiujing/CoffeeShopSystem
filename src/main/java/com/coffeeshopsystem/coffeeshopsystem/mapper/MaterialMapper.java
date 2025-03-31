package com.coffeeshopsystem.coffeeshopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.Material;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MaterialMapper extends BaseMapper<Material> {
}