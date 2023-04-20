package com.queshen.controller;

import com.queshen.pojo.bo.Result;
import com.queshen.service.IVoucherOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 前端控制器
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Slf4j
@RestController
@RequestMapping("/vouoreder")
public class VoucherOrderController {

    @Resource
    private IVoucherOrderService voucherOrderService;

    /**
     * 卡券下单功能
     * @Param 卡券id
     * @return 订单id
     */
    @PostMapping("/booking")
    public Result voucherBooking(@RequestParam("voucherId") String voucherId,
                                 @RequestParam("orderId") String orderId,
                                 @RequestParam("openid") String openid){

        return voucherOrderService.voucherBooking(voucherId,orderId,openid);
    }

    /**
     * 根据用户id查询用户拥有券的总数量
     * @Param userid
     * @Return 卡券数量
     */
    @PostMapping("/countvoucher")
    public Result countVoucherById(@RequestParam("openId") String openId){
        return voucherOrderService.countVoucherById(openId);
    }

    /**
     *  根据当前用户id查询该用户拥有的卡券
     * @Param userid
     * @Retrun 卡券信息
     */
    @PostMapping("/queryvoucher")
    public Result queryVoucherById(@RequestParam("openId") String openId){
        return  voucherOrderService.queryVoucherById(openId);}

    /**
     * 根据条件（房间是否适用，时长是否超时）判断卡券是否适用
     */
    @PostMapping("/voucherjudge")
    public Result voucherJudgement(@RequestParam("openId") String openId,
                                   @RequestParam("roomName") String roomName,
                                   @RequestParam("userTime") BigDecimal userTime)
    {return voucherOrderService.voucherJudgement(openId,roomName,userTime);}
}
