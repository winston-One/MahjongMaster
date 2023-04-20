package com.queshen.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisCache<T> {

    private RedisTemplate<String,Object> redisTemplate;

    private long duration;

    private TimeUnit timeUnit;

    private String prefix = "";

    /**
     * 返回key对应的value
     * @param key
     * @return
     */
    public T get(String key){

        T cacheObject = (T) redisTemplate.opsForValue().get(prefix+":"+key);
        return cacheObject;

    }

    public void put(String key,Object object){
        if (duration != 0){
            redisTemplate.opsForValue().set(prefix+":"+key,object,duration,timeUnit);
        }else {
            redisTemplate.opsForValue().set(prefix+key,object);
        }
    }

    public void remove(String key){
        redisTemplate.delete(prefix+":"+key);
    }

}
