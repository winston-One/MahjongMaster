package com.queshen.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.queshen.pojo.dto.MahjongOrderDTO;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Order;
import com.queshen.exceptionhandler.PayReCallException;
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

    @PostMapping("/pay")
    public Result pay(@RequestBody MahjongOrderDTO mahjongOrderDTO) throws WxPayException {
        String currentOpenid = mahjongOrderDTO.getOpenid();
        LocalDateTime now = LocalDateTime.now();
        //保存订单到数据库
        String id = mahjongOrderDTO.getOrderNo();
        BigDecimal realPrice = mahjongOrderDTO.getRealPrice();
        WxPayUnifiedOrderRequest orderReq = new WxPayUnifiedOrderRequest();
        orderReq.setBody("国粹娱乐中心");
        orderReq.setOutTradeNo(id);
        // 将金钱精确到两位小数
        orderReq.setOpenid(currentOpenid);
        orderReq.setSpbillCreateIp(mahjongOrderDTO.getIp());
        orderReq.setTotalFee(BaseWxPayRequest.yuanToFen(realPrice.toString()));
        String expireTime = now.plusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String startTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        orderReq.setTimeExpire(expireTime);
        orderReq.setTimeStart(startTime);
        orderReq.setNotifyUrl("http://localhost:9790/business/callback");
        orderReq.setTradeType("JSAPI");
        return Result.ok(wxPayService.createOrder(orderReq));
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
            }else {
                return WxPayNotifyResponse.success(payReCallException.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }
}
