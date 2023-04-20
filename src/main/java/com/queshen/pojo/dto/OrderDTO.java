package com.queshen.pojo.dto;

import com.queshen.pojo.po.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OrderDTO  {
    private Date expireTime;

    //订单id
    private String id;

    //用户Id
    private String userId;

    //房间Id
    private String roomId;

    //金额
    private BigDecimal price;


    //订单开始时间
    private LocalDateTime startTime;

    //订单结束时间
    private LocalDateTime endTime;

    //门店号
    private String storeId;

    //订单状态 1为已完成 2为进行中 3为已取消
    private Integer status;

    private String partnerID;

    //图片
    private String Image;

    //查看是否有用券 0为没有，1为美团的券，2为自己平台的券
    private Integer isVoucher;

    private String voucherId;

}
