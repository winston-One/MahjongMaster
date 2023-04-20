package com.queshen.pojo.bo;

import lombok.Data;

import java.util.List;

/**
 * 预约信息，存储哪个房间的哪一天的预约时间段
 * @author WinstonYv
 * @create 2022/11/14
 * @Description:
 */
@Data
public class DateInfo {

    /**
     * 当日日期
     */
    private String date;

    /**
     * 当天的预约信息
     */
    private List<TimeRange> timeRanges;

}
