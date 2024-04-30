package com.queshen.pojo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 卡券实体类
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponVo {

    private String voucherId;

    // 卡券标题
    private String title;

    // 可用范围
    private String availableRange;

    // 价格
    private BigDecimal price;

    // 有效期
    private Integer term;

    // 状态
    private Integer vouStatus;

    // 原价
    private Integer originalPrice;

    // 使用时长
    private BigDecimal duration;

}
