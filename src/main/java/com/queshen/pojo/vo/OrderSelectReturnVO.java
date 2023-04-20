package com.queshen.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class OrderSelectReturnVO {
    private String roomName;
    private String storeName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String duration;
    private Integer orderStatus;
    private String imgUrl;
    private BigDecimal price;
    private Integer isVoucher;
    private String voucherId;
    private String orderId;
    private LocalDateTime creatTime;
    private LocalDateTime payTime;
}
