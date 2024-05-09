package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherOrderDTO {

    /**
     * 卡券id
     */
    private String voucherId;

    /**
     * 卡券订单id
     */
    private String id;
    /**
     * 卡券标题
     */
    private String title;

    /**
     * 可用范围
     */
    private String availableRange;

    /**
     * 有效期
     */
    private Integer term;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 订单状态(1.有效2.无效3.已使用)
     */
    private Integer orderStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
