package com.queshen.pojo.vo;

import lombok.Data;

//用户查询订单VO
@Data
public class OrderSelectByUserVO {

    //用户的openId
    private String openId;

    //订单状态  1为进行中，2为未开始，3为已完成
    private Integer orderStatus;

    //排序状态
    private Integer sortStatus;

    //当前页码
    private Integer pageNum;

    //门店号
    private String storeId;
}
