package com.queshen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author WinstonYv
 * @since 2022/11/14
 */
@Configuration
public class MvcConfig {

    @Resource
    private StringRedisTemplate stringRedisTemplate;



}
