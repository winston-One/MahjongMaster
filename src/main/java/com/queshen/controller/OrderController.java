package com.queshen.controller;

import com.queshen.pojo.bo.Result;
import com.queshen.service.OrderService;
import com.queshen.pojo.vo.OrderSaveVo;
import com.queshen.pojo.vo.OrderSelectByUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 * 和订单相关的控制器
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 获取该用户所有类型订单信息（可按类型）
     */
    @PostMapping("/getAllOrderByUser")
    public Result getAllOrderByUser(@RequestBody OrderSelectByUserVO orderSelectByUserVO){
        return orderService.getAllOrderByUser(orderSelectByUserVO);
    }

    /**
     * 下单，同一个用户1秒内不能重复请求
     */
    @PostMapping("/saveOneUserOrder")
//    @RepeatRequest
    public Result saveOneUserOrder(@RequestBody OrderSaveVo orderSaveVo){
//        if (orderService.judgeTimeExist(orderSaveVo))
//            return orderService.saveOneUserOrder(orderSaveVo);
//        else
//            return Result.fail("房间被人预定");
        return orderService.saveOneUserOrder(orderSaveVo);
    }


    /**
     * 删除进行中订单
     * @param orderSaveVo [userId:用户id  id:订单id]
     * @return ture/false
     */
    @PostMapping("/deleteOrder")
    public Result deleteOrder(@RequestBody OrderSaveVo orderSaveVo){
        return orderService.deleteOrder(orderSaveVo);
    }

    /**
     * 查询用户是否存在进行中订单
     * @return
     */
    @GetMapping("/selectIsDoingOrder")
    public Result selectIsDoingOrder(){
        return Result.ok(orderService.selectIsDoingOrder());
    }

}
