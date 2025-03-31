package com.coffeeshopsystem.coffeeshopsystem.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.coffeeshopsystem.coffeeshopsystem.mapper")
public class MyBatisPlusConfig {
}
