package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Order;
import com.queshen.pojo.vo.OrderSaveVo;
import com.queshen.pojo.vo.OrderSelectByUserVO;
import org.springframework.stereotype.Service;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public interface OrderService extends IService<Order> {

    Result getAllOrderByUser(OrderSelectByUserVO orderSelectByUserVO);

    Result saveOneUserOrder(OrderSaveVo orderSaveVo);

    Result userPointOrder(OrderSaveVo OrderSaveVo);

    Result deleteOrder(OrderSaveVo orderSaveVo);

    // 检查是否是交易成功
    boolean checkPaySuccess(WxPayOrderNotifyResult result);

    Boolean judgeTimeExist(OrderSaveVo orderSaveVo);

    Boolean selectIsDoingOrder();
}
