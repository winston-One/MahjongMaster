package com.queshen.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.queshen.pojo.dto.BusinessOrderDTO;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Order;
import com.queshen.exceptionhandler.PayReCallException;
import com.queshen.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author winston
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
    public Result pay(@RequestBody BusinessOrderDTO businessOrderDTO) throws WxPayException {
        String currentOpenid = businessOrderDTO.getOpenid();
        LocalDateTime now = LocalDateTime.now();
        //保存订单到数据库
        String id = businessOrderDTO.getOrderNo();
        BigDecimal realPrice = businessOrderDTO.getRealPrice();
        //计算实际支付价格
//        BigDecimal realPrice = new BigDecimal(0);
//        List<PayGoodsDTO> goodsList = businessOrderMongo.getGoodsList();
//        for (PayGoodsDTO dto : goodsList){
//            realPrice = realPrice.add(dto.getPrice());
//            List<PayPropertyItemDTO> propertyItemList = dto.getPropertyItemList();
//            if (propertyItemList != null){
//                for (PayPropertyItemDTO propertyDto : propertyItemList){
//                    if (propertyDto != null){
//                        PropertyItem propertyItem = propertyItemService.getById(propertyDto.getId());
//                        if (propertyItem.getPrice() != null){
//                            realPrice = realPrice.add(propertyItem.getPrice());
//                        }
//                    }
//                }
//            }
//        }
//        if (businessOrderMongo.getDeliverCustomPrice() != null){
//            realPrice = realPrice.add(businessOrderMongo.getDeliverCustomPrice());
//        }
//        if (businessOrderMongo.getTotalPackFee() != null){
//            realPrice = realPrice.add(businessOrderMongo.getTotalPackFee());
//        }
//        if (businessOrderMongo.getRedFee() != null){
//            realPrice = realPrice.subtract(businessOrderMongo.getRedFee());
//        }

        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody("马上到国粹");
        orderRequest.setOutTradeNo(id);
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(realPrice.toString()));//元转成分
        orderRequest.setOpenid(currentOpenid);
        orderRequest.setSpbillCreateIp(businessOrderDTO.getIp());
        String startTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String expireTime = now.plusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        orderRequest.setTimeStart(startTime);
        orderRequest.setTimeExpire(expireTime);
        orderRequest.setNotifyUrl("http://localhost:7777/business/payRecall");
        orderRequest.setTradeType("JSAPI");
        return Result.ok(wxPayService.createOrder(orderRequest));//.("orderId", id);
    }

    @PostMapping("/payRecall")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);
            String orderId = result.getOutTradeNo();
            Order order = orderService.getById(orderId);
            log.info("微信支付回调，订单号 {}", orderId);
            if (order != null) {
                return WxPayNotifyResponse.success("支付成功");
            }
            if (order.getStatus() == 2) {
                // log.info("该笔订单已支付{}，但被回调", orderId);
                return WxPayNotifyResponse.success("支付成功");
            }
            if (!orderService.checkPaySuccess(result)) {
                // log.info("未交易成功回调 {}", orderId);
                return WxPayNotifyResponse.success("未交易成功回调");
            }
            return WxPayNotifyResponse.success("支付成功");
        } catch (PayReCallException payReCallException) {
            log.info("打印微信支付异常信息{}", payReCallException.getMessage());
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
