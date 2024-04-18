package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *  * @author WinstonYv
 *  * @create 2022/11/14
 *  * @Description:
 * 卡券订单实体类
 */
@TableName("tb_voucher_order")
@Data
public class VoucherOrder {

    private static final long serialVersionUID = 1L;

    // 主键
    @TableId
    private String id;

    // 购买的卡券id
    private String voucherId;

    // 下单的用户id
    private String userId;

    // 订单状态(1.有效2.无效3.已使用)
    private Integer orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    // 下单时间
    private LocalDateTime createTime;

    // 支付时间
    private LocalDateTime payTime;

    public VoucherOrder(String id, String voucherId, String userId, Integer orderStatus, LocalDateTime createTime) {
        this.id = id;
        this.voucherId = voucherId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
    }
}
