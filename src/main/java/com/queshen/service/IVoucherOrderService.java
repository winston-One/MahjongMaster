package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.VoucherOrder;

import java.math.BigDecimal;

/**
 * 卡券订单相关服务
 */
public interface IVoucherOrderService extends IService<VoucherOrder> {

    Result voucherBooking(String voucherId,String orderId,String openid);

    Result countVoucherById(String openId);

    Result queryVoucherById(String openId);

    Result changeOrderStatus(String orderId);

    Result voucherJudgement(String openId,String roomName, BigDecimal userTime);

}
