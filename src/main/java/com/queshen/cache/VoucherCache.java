package com.queshen.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.pojo.po.Voucher;
import com.queshen.service.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author winston
 * @create 2023/4/10 10:45
 * @Description: Man can conquer nature
 * caffeine本地缓存配置
 **/
@Configuration
public class VoucherCache {

    @Autowired
    IVoucherService voucherService;

    /**
     * 用于做优惠券的本地缓存处理
     * @return
     */
    @Bean("voucherListCache")
    public LoadingCache<String, List<Voucher>> getCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(3, TimeUnit.DAYS)
                .build(key -> voucherService.list());
    }

}
