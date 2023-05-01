package com.queshen.controller;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.pojo.dto.PointAfterDTO;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.VoucherOrderDTO;
import com.queshen.pojo.po.DianPingVoucherOrder;
import com.queshen.service.DianPingVoucherService;
import com.queshen.service.IVoucherOrderService;
import com.queshen.service.OrderService;
import com.queshen.pojo.vo.OrderSaveVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 * 如果使用的美团券或者商家券，支付完成之后需要将该券标明已用
 */
@Log4j2
@RequestMapping("/PointAfter")
@RestController
public class PointAfterController {

    @Autowired
    OrderService orderService;

    @Autowired
    DianPingVoucherService dianPingVoucherService;

    @Autowired
    IVoucherOrderService voucherOrderService;

    @Resource(name = "voucherListCache")
    LoadingCache<String, List<VoucherOrderDTO>> voucherListCache;

    @PostMapping("/pointAfterDo")
    public Result pointAfterDo(@RequestBody PointAfterDTO pointAfterDTO){
        //用了美团券,支付完之后调用
        if (pointAfterDTO.getIsVoucher() == 1){
            DianPingVoucherOrder dianPingVoucherOrder = new DianPingVoucherOrder();
            dianPingVoucherOrder.setUserId(pointAfterDTO.getUserId());
            dianPingVoucherOrder.setId(pointAfterDTO.getVoucherId());
            dianPingVoucherService.doDPOrderToMysql(dianPingVoucherOrder);
        }
        //用了平台券 支付完之后调用
        if(pointAfterDTO.getIsVoucher() == 2){
            voucherOrderService.changeOrderStatus(pointAfterDTO.getVoucherId());
            voucherListCache.invalidate(pointAfterDTO.getUserId());
        }
        //支付完之后调用
        OrderSaveVo orderSaveVo = new OrderSaveVo();
        orderSaveVo.setId(pointAfterDTO.getOrderId());
        orderSaveVo.setUserId(pointAfterDTO.getUserId());
        orderService.userPointOrder(orderSaveVo);
        return Result.ok();
    }
}
