package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.queshen.pojo.po.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 数据库订单实体
 **/

@TableName("tb_order")
@Data
public class Order extends BaseEntity {

    //订单id
    @TableId("order_id")
    private String id;

    //用户Id
    private String userId;

    //房间Id
    private String roomId;

    //金额
    private BigDecimal price;


    // 订单开始时间
    private LocalDateTime startTime;

    // 订单结束时间
    private LocalDateTime endTime;

    // 门店号
    private String storeId;

    // 订单状态 1为已完成 2为进行中 3为已取消
    @TableField(value = "order_status")
    private Integer status;

    //商户号
    @TableField("partner_id")
    private String partnerID;

    // 查看是否有用券 0为没有，1为美团的券，2为自己平台的券
    private Integer isVoucher;

    private String voucherId;

    // 图片路径
    private String image;

    //支付时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime payTime;
}
