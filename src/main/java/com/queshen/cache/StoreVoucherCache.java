package com.queshen.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.mapper.VoucherMapper;
import com.queshen.pojo.po.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author WinstonYv
 * @create 2023/4/10 10:45
 * @Description: Man can conquer nature
 * caffeine本地缓存配置
 **/
@Configuration
public class StoreVoucherCache {

    @Resource
    VoucherMapper voucherMapper;

    /**
     * 用于做优惠券的本地缓存处理，将openid作为缓存key
     */
    @Bean("voucherStoreCache")
    public LoadingCache<String, List<Voucher>> getCache(){
        return Caffeine.newBuilder()
            .expireAfterWrite(3, TimeUnit.DAYS)
            .build(key -> {
                QueryWrapper<Voucher> voucherQueryWrapper = new QueryWrapper<>();
                voucherQueryWrapper.eq("vou_status", 1);
                voucherQueryWrapper.eq("is_delete", 0);
                return voucherMapper.selectList(voucherQueryWrapper);
            });
    }
}
