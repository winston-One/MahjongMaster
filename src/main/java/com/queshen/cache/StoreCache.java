package com.queshen.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.pojo.po.Store;
import com.queshen.service.StoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author WinstonYv
 * @create 2023/4/26 23:07
 * @Description: Man can conquer nature
 * 将所有的门店进行缓存
 **/
@Configuration
public class StoreCache {

    @Resource
    StoreService storeService;

    /**
     * 用于做优惠券的本地缓存处理
     */
    @Bean("storeListCache")
    public LoadingCache<String, List<Store>> getCache(){
        return Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.DAYS)// 缓存门店数据的有效期是30天
            .build(key -> storeService.list());
    }
}
