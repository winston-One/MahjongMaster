package com.queshen.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 返回给前端订单的信息
 **/
@Data
public class OrderSelectReturnVO {

    // 房间名字
    private String roomName;

    // 门店名字
    private String storeName;

    // 麻将馆预约开始时间
    private LocalDateTime startTime;

    // 麻将馆预约的结束时间
    private LocalDateTime endTime;

    // 一共预约了几个小时
    private String duration;

    // 当前订单状态，未支付，已支付，已完成,具体看OrderStatus类
    private Integer orderStatus;

    private String imgUrl;

    private BigDecimal price;

    // 是否使用优惠券
    private Integer isVoucher;

    private String voucherId;

    private String orderId;

    // 下单时间
    private LocalDateTime createTime;

    // 支付时间
    private LocalDateTime payTime;

}
