package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 卡券订单实体类
 */

@TableName("tb_voucher_order")
@Data
public class VoucherOrder {

    // 主键
    @TableId
    private String id;

    // 购买的卡券id
    private String voucherId;

    // 下单的用户id
    private String userId;

    // 订单状态(1.有效2.无效3.已使用)
    private Integer orderStatus;

    // 下单时间
    private LocalDateTime createTime;

    // 支付时间
    private LocalDateTime payTime;

}
