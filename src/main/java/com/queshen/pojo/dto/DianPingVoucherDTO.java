package com.queshen.pojo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
@Data
public class DianPingVoucherDTO {

    //订单id
    private String order_id;

    //验证券码
    private String receipt_code;

    //商品名称
    private String deal_title;

    //业务类型
    private Integer biz_type;

    //支付细节
    private List<PaymentDetailDTO> payment_detail;

    //券过期时间
    private Date receiptEndDate;

    //第三方的店铺id，不提倡使用，app_shop_id和open_shop_uuid二选一，必须填写一个
    private String app_shop_id;

    //美团点评店铺id，建议使用，app_shop_id和open_shop_uuid二选一，必须填写一个
    private String open_shop_uuid;

    ////套餐id（若验证的券所对应的商品为团购时，该字段必返回）
    private Long deal_id;

    //团购id,团购id与套餐id是一对多的关系（若验证的券所对应的商品为团购时，该字段必返回）
    private Long dealgroup_id;

    //商品id（若验证的券所对应的商品非团购时，该字段必返回，product_item_id含义参考商品管理API）
    private Long product_item_id;

    //商品类型 1、泛商品如丽人派样活动商品等（若验证的券所对应的商品非团购时，该字段必返回）
    private Integer product_type;

    //商品售卖价格
    private Double deal_price;

    //商品市场价
    private Double deal_marketprice;

    //用户手机号，形如：185****1212
    private String mobile;

    //是否展示完整号码,1:展示；0或null：隐藏
    private Integer disclose_mobile_no;

}
