package com.queshen.controller;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.anno.RepeatRequest;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.VoucherOrderDTO;
import com.queshen.service.IVoucherOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 前端控制器
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Slf4j
@RestController
@RequestMapping("/voucherUser")
public class VoucherOrderController {

    @Resource
    private IVoucherOrderService voucherOrderService;

    @Resource(name = "voucherListCache")
    LoadingCache<String, List<VoucherOrderDTO>> voucherListCache;

    /**
     * 卡券下单功能
     */
    @PostMapping("/booking")
    @RepeatRequest(intervalTime = 1)
    public Result voucherBooking(@RequestParam("voucherId") String voucherId,
                                 @RequestParam("orderId") String orderId,
                                 @RequestParam("openid") String openid){
        // 该用户所拥有的卡券发生变化，所以清除对应的本地缓存，通过key进行清除
        voucherListCache.invalidate(openid);
        return voucherOrderService.voucherBooking(voucherId,orderId,openid);
    }

    /**
     * 根据用户id查询用户拥有券的总数量
     */
    @PostMapping("/countVoucher")
    public Result countVoucherById(@RequestParam("openId") String openId){
        return voucherOrderService.countVoucherById(openId);
    }

    /**
     *  根据当前用户id查询该用户拥有的卡券
     */
    @PostMapping("/getVoucher")
    public Result queryVoucherById(@RequestParam("openId") String openId){
        List<VoucherOrderDTO> vouchers = voucherListCache.get(openId);
        return Result.ok(vouchers);
    }

    /**
     * 根据条件（房间是否适用，时长是否超时）判断卡券是否适用
     */
    @PostMapping("/judgeVoucher")
    public Result voucherJudgement(@RequestParam("openId") String openId,
                                   @RequestParam("roomName") String roomName,
                                   @RequestParam("userTime") BigDecimal userTime)
    {return voucherOrderService.voucherJudgement(openId,roomName,userTime);}
}
