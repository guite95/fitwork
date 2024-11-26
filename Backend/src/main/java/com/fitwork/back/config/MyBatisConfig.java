package com.fitwork.back.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.fitwork.back.*.model.repository")
public class MyBatisConfig {
	
}