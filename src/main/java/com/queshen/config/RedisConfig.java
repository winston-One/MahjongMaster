package com.queshen.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置类
 * @author WinstonYv
 * @since 2022/11/14
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedissonClient redissonClient(){
        // 配置

        Config config = new Config();
        config.useSingleServer().setAddress("redis://43.143.88.250:52515").setPassword("u?5*2%e-5/r+");
        // 创建RedissonClient对象
        return Redisson.create(config);
    }
}
