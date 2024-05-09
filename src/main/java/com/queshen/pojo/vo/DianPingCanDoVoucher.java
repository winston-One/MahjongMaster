package com.queshen.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
//美团可用券返回信息
public class DianPingCanDoVoucher {

    //id
    private String id;

    //价格
    private BigDecimal price;

    //时长
    private BigDecimal duration;
}
