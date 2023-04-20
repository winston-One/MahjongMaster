package com.queshen.pojo.dto;

import lombok.Data;

@Data
public class PointAfterDTO {
    //用户id
    private String userId;
    //订单id
    private String orderId;
    ////查看是否有用券 0为没有，1为美团的券，2为自己平台的券
    private Integer isVoucher;
    //券订单id
    private String voucherId;
}
