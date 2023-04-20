package com.queshen.config;

import com.queshen.cache.RedisCache;
import com.queshen.cache.RedisCacheBuilder;
import com.queshen.pojo.po.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author winston
 * @create 15:41
 * @Description:
 **/
@Configuration
public class LoginCacheConfig {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 登录的本地缓存处理
     * @return
     */
    @Bean("loginCache")
    public RedisCache<User> getCache(){
        return new RedisCacheBuilder<User>(redisTemplate).setPrefix("user")
                .expire(30,TimeUnit.DAYS).build();
    }

}
