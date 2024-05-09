package com.queshen.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Setter
@Getter
public class ReceiptValidateResultDTO {

    //验证券码
    public String receipt_code;

    //套餐id（若验证的券所对应的商品为团购时，该字段必返回）
    public Long deal_id;

    //团购id,团购id与套餐id是一对多的关系（若验证的券所对应的商品为团购时，该字段必返回）
    public Long deal_group_id;

    //商品id（若验证的券所对应的商品非团购时，该字段必返回，product_item_id含义参考商品管理API）
    public Long product_item_id;

    //商品类型 1、泛商品如丽人派样活动商品等（若验证的券所对应的商品非团购时，该字段必返回）
    public Integer product_type;

    //商品名称
    public String deal_title;

    //商品售卖价格
    public Double deal_price;

    //商品市场价
    public Double deal_market_price;

    //业务类型 0:普通团购 203:拼团 205:次卡
    public Integer biz_type;

    //券过期时间
    public Date receiptEndDate;

    //用户手机号
    public String mobile;
}
