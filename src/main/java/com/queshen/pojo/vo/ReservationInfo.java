package com.queshen.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author VioletQin
 * @since 2022/12/18
 */
@Data
@Builder
public class ReservationInfo {
    /** 时间段*/
    private String time;
    /** 是否能预约 0->未预约。 1->已预约*/
    private Integer isFree;
    /** 是否为次日  0->否，1->是*/
    private Integer isNextDay;
}
