package com.queshen.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisCacheBuilder<T> {

    public static final int NO_EXPIRE = 0;

    public static final int ACCESS_EXPIRE = 1;

    // 过期时间
    private long duration;

    // 过期时间的单位
    private TimeUnit timeUnit;

    private RedisTemplate<String,Object> redisTemplate;

    // 存入到redis的前缀
    private String prefix = "";

    // 设置过期时间
    public RedisCacheBuilder<T> expire(long duration, TimeUnit timeUnit) {
        this.duration = duration;
        this.timeUnit = timeUnit;
        return this;
    }

    /**
     * 设置过期时间
     * @return
     */
    public RedisCacheBuilder<T> setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public RedisCacheBuilder(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //构建对应的redis缓存
    public RedisCache<T> build(){
        return new RedisCache<>(redisTemplate,duration,timeUnit,prefix);
    }
}
