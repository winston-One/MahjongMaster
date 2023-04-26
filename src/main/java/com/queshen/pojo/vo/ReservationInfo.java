package com.queshen.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@Builder
public class ReservationInfo {

    /** 可预约的时间段*/
    private String time;

    /** 是否能预约 0->未预约。 1->已预约*/
    private Integer isFree;

    /** 是否为次日的时间段  0->否，1->是*/
    private Integer isNextDay;

}
