package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSuitDTO {

    /**
     * 卡券id
     */
    private String voucherId;
    /**
     * 标题
     */
    private String title;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 时长
     */
    private BigDecimal duration;

}
