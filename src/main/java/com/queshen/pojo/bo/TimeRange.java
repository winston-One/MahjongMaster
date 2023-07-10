package com.queshen.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 被预约的时间段信息
 * @author WinstonYv
 * @since 2022/12/2
 */
@Data
@AllArgsConstructor
public class TimeRange {

    /**
     * 对应时间戳
     */
    private Long startTimestamp;

    /**
     * 是否被预定
     */
    private Integer isReservation;
}
