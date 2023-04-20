package com.queshen.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDetailDTO {
    //支付详情id
    public String payment_detail_id;
    //支付金额
    public BigDecimal amount;
    //金额类型
    public Long amount_type;


}
