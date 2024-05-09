package com.queshen.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class PaymentDetailDTO {

    //支付详情id
    public String payment_detail_id;

    //支付金额
    public BigDecimal amount;

    //金额类型
    public Long amount_type;
}
