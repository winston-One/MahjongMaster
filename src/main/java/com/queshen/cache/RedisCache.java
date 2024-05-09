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
public class RedisCache<T> {

    private RedisTemplate<String,Object> redisTemplate;

    private long duration;

    private TimeUnit timeUnit;

    private String prefix = "";

    /**
     * 返回key对应的value
     * 在Redis中设置前缀prefix并且使用冒号与key隔开，相当于新建一层文件夹目录
     */
    public T get(String key){
        return (T) redisTemplate.opsForValue().get(prefix+":"+key);
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
