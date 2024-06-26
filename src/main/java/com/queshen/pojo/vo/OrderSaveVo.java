package com.queshen.pojo.vo;

import com.queshen.pojo.dto.VoucherSuitDTO;
import com.queshen.pojo.po.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaveVo extends BaseEntity {

    //订单id
    private String id;

    //用户Id
    private String userId;

    //房间Id
    private String roomId;

    //金额
    private BigDecimal price;

    //订单开始时间
    private Date startTime;

    //订单结束时间
    private Date endTime;

    private VoucherSuitDTO coupon;

    //门店号
    private String storeId;

    //订单状态 1为已完成 2为进行中 3为已取消
    private Integer status;

    private String partnerID;

    private String voucherId;

    // 该订单是否使用过优惠券
    private Integer isVoucher;
}


