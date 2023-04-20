package com.queshen.controller;

import com.queshen.config.RedisConfig;
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
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    RedisConfig redisConfig;



    /**
     * 获取该用户所有类型订单信息（可按类型）
     * @param orderSelectByUserVO
     *        [ "openId" : 用户id,
     *          "orderStatus" : 订单状态 ,
     *          "storeId" : 门店号 ,
     *          "pageNum" : 当前页数
     *        ]
     * @return
     */
    @PostMapping("/getAllOrderByUser")
    public Result getAllOrderByUser(@RequestBody OrderSelectByUserVO orderSelectByUserVO){
        return orderService.getAllOrderByUser(orderSelectByUserVO);
    }

    /**
     * 下单
     *
     * @param orderSaveVo
     *    ["roomId" : 房间id,
     *     "userId" : 用户id ,
     *     "status" : 订单状态 ,
     *     "storeId" : 门店号 ,
     *     "price" : 价格,
     *     "startTime" :订单开始时间,
     *     "endTime": 订单结束时间]
     * @return List[order[
     *                  "createTime": 创建时间
     *                 "createUser": 创建用户
     *                 "updateTime": 更新时间
     *                 "id": 订单id
     *                 "userId": 用户id
     *                 "roomId": 房间id
     *                 "price": 66.00,
     *                 "startTime": 开始时间
     *                 "endTime": 结束时间
     *                 "storeId": 门店号
     *                 "status": 订单状态
     *
     * ]]
     */
    @PostMapping("/saveOneUserOrder")
    public Result saveOneUserOrder(@RequestBody OrderSaveVo orderSaveVo){
        if (orderService.judgeTimeExist(orderSaveVo))
            return orderService.saveOneUserOrder(orderSaveVo);
        else
            return Result.fail("房间被人预定");
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

//    /**
//     * 支付完成后调用
//     * @param OrderSaveVo [userId:用户id  id:订单id]
//     * @return
//     */
//    @PostMapping("/userPointOrder")
//    public Result userPointOrder(@RequestBody OrderSaveVo OrderSaveVo){
//        return  orderService.userPointOrder(OrderSaveVo);
//    }

    /**
     * 查询用户是否存在进行中订单
     * @return
     */
    @GetMapping("/selectIsDoingOrder")
    public Result selectIsDoingOrder(){
        return Result.ok(orderService.selectIsDoingOrder());
    }


}
