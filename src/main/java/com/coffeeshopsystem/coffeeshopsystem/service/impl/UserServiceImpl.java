package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.mapper.UserMapper;
import com.coffeeshopsystem.coffeeshopsystem.model.User;
import com.coffeeshopsystem.coffeeshopsystem.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}