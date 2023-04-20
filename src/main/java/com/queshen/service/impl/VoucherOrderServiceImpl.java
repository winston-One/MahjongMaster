package com.queshen.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.VoucherOrderDTO;
import com.queshen.pojo.dto.VoucherSuitDTO;
import com.queshen.pojo.po.Voucher;
import com.queshen.pojo.po.VoucherOrder;
import com.queshen.mapper.VoucherOrderMapper;
import com.queshen.service.DianPingVoucherService;
import com.queshen.service.IVoucherOrderService;
import com.queshen.service.IVoucherService;
import com.queshen.utils.RedisIdWorker;
import com.queshen.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Slf4j
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Resource
    private IVoucherService voucherService;

    @Resource
    private IVoucherOrderService voucherOrderService;

    @Resource
    private VoucherOrderMapper voucherOrderMapper;

    @Resource
    private RedisIdWorker redisIdWorker;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private DianPingVoucherService dianPingVoucherService;

    @Override
    @Transactional
    public Result voucherBooking(String voucherId,String orderId,String openid) {
        // 1.查询卡券信息
        Voucher voucher = voucherService.getById(voucherId);
        // 2.判断卡券状态是否为下架
        if (voucher.getVouStatus() == 2){
            // 返回错误信息
            return Result.fail("订单已下架！");
        }
        // 3.获取当前用户id
        String userId = openid;
        // 4.创建订单
        VoucherOrder voucherOrder = new VoucherOrder();
        // 4.1.用户id
        voucherOrder.setUserId(userId);
        // 4.2.卡券id
        voucherOrder.setVoucherId(voucherId);
        // 4.3.订单状态
        voucherOrder.setOrderStatus(1);
        // 4.4.创建时间
        voucherOrder.setCreateTime(LocalDateTime.now());
        // 5.存入Redis（设置过期时间）和Mysql中
        // 5.1.存入Redis并设置过期时间
        saveVoucherOrderToRedis(orderId,voucherOrder,voucher.getTerm());
        // 5.2.存入Mysql
        voucherOrder.setId(orderId);
        voucherOrderService.save(voucherOrder);
        // 6.返回成功
        return Result.ok("下单成功");
    }

    @Override
    @Transactional
    public Result countVoucherById(String openId) {
        // 根据用户id查询用户拥有卡券进行计数
        int count = query()
                .eq("user_id", openId)
                .eq("order_status", 1)
                .count();
        // 调用dianPingVoucherService中的查看用户可用美团券
        Result result = dianPingVoucherService.selectDPOrderInRedis(openId, 1);
        if (result.getTotal() == null){
            int total = 0;
            return Result.ok(count);
        }else{
            int total = result.getTotal().intValue();
            return Result.ok(count + total);
        }
    }

    @Override
    @Transactional
    public Result queryVoucherById(String openId) {
        List<VoucherOrderDTO> voucherOrderDTOS = voucherOrderMapper.listVoucherOrder(openId);
        if (voucherOrderDTOS ==null){
            return null;
        }else{
            return Result.ok(voucherOrderDTOS);
        }
    }

    @Override
    public Result changeOrderStatus(String orderId) {
        // 获取当前用户id
        String openid = UserHolder.getUser().getOpenid();
        UpdateWrapper<VoucherOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("order_status",3).eq("user_id",openid).eq("id",orderId);
        int result = voucherOrderMapper.update(null, updateWrapper);
        if (result > 0){
            return Result.ok("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    @Override
    public Result voucherJudgement(String openId,String roomName, BigDecimal duration) {
        List<VoucherSuitDTO> voucherSuitDTOS = voucherOrderMapper.voucherJudgement(openId,roomName,duration);
        if (voucherSuitDTOS == null){
            return null;
        }else{
            return Result.ok(voucherSuitDTOS);
        }

    }

    private void saveVoucherOrderToRedis(String orderId,VoucherOrder voucherOrder,int term) {
        // 设置key
        String key = "cache:vou_order:" + orderId;
        // 1.获取卡券的有效期
        // 2.判断term的值
        if (term == 0){
            // 3.0为不限制
            stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(voucherOrder));
        }else {
            // 4.其余值，存入Redis，设置TTL的值为term，单位为天
            stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(voucherOrder),term, TimeUnit.DAYS);
        }

    }


}
