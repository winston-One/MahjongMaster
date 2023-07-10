package com.queshen.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.mapper.VoucherOrderMapper;
import com.queshen.pojo.dto.VoucherOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author WinstonYv
 * @create 2023/4/10 10:45
 * @Description: Man can conquer nature
 * caffeine本地缓存配置
 **/
@Configuration
public class VoucherUserCache {

    @Autowired
    VoucherOrderMapper voucherOrderMapper;

    /**
     * 用于做优惠券的本地缓存处理，将openid作为缓存key
     * @return
     */
    @Bean("voucherListCache")
    public LoadingCache<String, List<VoucherOrderDTO>> getCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(3, TimeUnit.DAYS)
                .build(key -> voucherOrderMapper.listVoucherOrder(key));
    }
}
