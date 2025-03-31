package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MenuMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.Menu;
import com.coffeeshopsystem.coffeeshopsystem.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}