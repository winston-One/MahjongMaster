package com.queshen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 配置线程池
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor(){
        return new ThreadPoolExecutor(
                2,
                16,
                8,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>()
        );
    }
}
