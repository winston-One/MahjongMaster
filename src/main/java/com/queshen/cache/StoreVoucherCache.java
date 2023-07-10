package com.queshen.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.mapper.VoucherMapper;
import com.queshen.pojo.po.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author WinstonYv
 * @create 2023/4/10 10:45
 * @Description: Man can conquer nature
 * caffeine本地缓存配置
 **/
@Configuration
public class StoreVoucherCache {

    @Autowired
    VoucherMapper voucherMapper;

    /**
     * 用于做优惠券的本地缓存处理，将openid作为缓存key
     * @return
     */
    @Bean("voucherStoreCache")
    public LoadingCache<String, List<Voucher>> getCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(3, TimeUnit.DAYS)
                .build(key -> {
                    Map<String,Object> map = new HashMap<>();
                    map.put("is_delete",1);
                    map.put("vou_status",1);
                    List<Voucher> vouchers = voucherMapper.selectByMap(map);
                    return vouchers;
                });
    }
}
