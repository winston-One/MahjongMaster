package com.queshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.queshen.constant.OrderStatus;
import com.queshen.mapper.OrderMapper;
import com.queshen.mapper.UserMapper;
import com.queshen.pojo.dto.MahjongOrderDTO;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Order;
import com.queshen.exceptionhandler.PayReCallException;
import com.queshen.pojo.po.User;
import com.queshen.pojo.po.VoucherOrder;
import com.queshen.service.IUserService;
import com.queshen.service.IVoucherOrderService;
import com.queshen.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author WinstonYv
 * @create 2022/12/18 11:29
 * @Description: Man can conquer nature
 **/
@RestController
@RequestMapping("/business")
@Slf4j
public class PayController {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IVoucherOrderService voucherOrderService;

    @Autowired
    IUserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    @PostMapping("/pay")
    public Result pay(@RequestBody MahjongOrderDTO dto) {
        String openid = dto.getOpenid();
        String orderId = dto.getOrderNo();
        BigDecimal totalFee = dto.getRealPrice();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        User user = userMapper.selectOne(queryWrapper);
        if (user.getMoney() < totalFee.doubleValue()) {
            return Result.fail("余额不足");
        }
        user.setMoney(user.getMoney()-totalFee.doubleValue());
        userService.saveOrUpdate(user);
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orderId);
        updateWrapper.set("order_status", OrderStatus.ORDER_IS_PAID);
        updateWrapper.set("pay_time", LocalDateTime.now());
        updateWrapper.set("update_time", LocalDateTime.now());
        orderService.update(updateWrapper);
        return Result.ok("支付成功");
    }

    @PostMapping("/payCoupon")
    public Result payCoupon(@RequestBody MahjongOrderDTO dto) {
        String openid = dto.getOpenid();
        String orderId = dto.getOrderNo();
        BigDecimal totalFee = dto.getRealPrice();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        User user = userMapper.selectOne(queryWrapper);
        if (user.getMoney() < totalFee.doubleValue()) {
            return Result.fail("余额不足");
        }
        user.setMoney(user.getMoney()-totalFee.doubleValue());
        userService.saveOrUpdate(user);

        UpdateWrapper<VoucherOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", orderId);
        updateWrapper.set("order_status", OrderStatus.ORDER_UN_PAID);
        updateWrapper.set("pay_time", LocalDateTime.now());
        voucherOrderService.update(updateWrapper);
        return Result.ok("支付成功");
    }

    @PostMapping("/callback")
    public String payNotify(HttpServletRequest request) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult payRes = wxPayService.parseOrderNotifyResult(xmlResult);
            String orderId = payRes.getOutTradeNo();
            Order order = orderService.getById(orderId);
            log.info("微信支付回调，订单号 {}", orderId);
            if (StringUtils.isNotEmpty(orderId)) {
                return WxPayNotifyResponse.success("支付成功");
            }
            if (order.getStatus() == 2) {
                log.info("订单已支付=={}", orderId);
                return WxPayNotifyResponse.success("支付成功");
            }
            if (!orderService.checkPaySuccess(payRes)) {
                log.info("未交易成功=={}", orderId);
                return WxPayNotifyResponse.success("未交易成功回调");
            }
            return WxPayNotifyResponse.success("支付成功");
        } catch (PayReCallException payReCallException) {
            log.info("微信支付异常==={}", payReCallException.getMessage());
            payReCallException.printStackTrace();
            if (payReCallException.getCode() == 444) {
                return WxPayNotifyResponse.fail(payReCallException.getMessage());
            } else {
                return WxPayNotifyResponse.success(payReCallException.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }
}
