package com.queshen.pojo.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author winston
 * @create 2022/12/22 18:33
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MahjongOrderDTO {

    private String openid;

    private String orderNo;

    private String ip;

    private BigDecimal realPrice;

}
