package com.queshen.utils;

import com.queshen.pojo.po.Order;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WinstonYv
 * @since 2022/12/16
 */
public class TimeRangeUtil {
    /** 半小时对应毫秒数*/
    public static final long halfHour = 30 * 60 * 1000;

    /**
     * 根据订单装配时间轴，使用TimeUnit
     */
    public static List<TimeRange> timeRanges(List<Order> orders, String date) {
        //获取对应时间戳
        List<TimeRange> timeRanges = getTimeRanges(date);
        //日期转换格式
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeRangeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Order order : orders) {
            String startTime = dateFormat.format(order.getStartTime());
            String endTime = dateFormat.format(order.getEndTime());
            //三种情况
            //1 startTime 和 endTime 与所需查询日期相同
            if(startTime.equals(date) && endTime.equals(date)) {
                //遍历timeRanges,找到对应时间戳并将其预定状态置为true
                Long startTimestamp = order.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                Long endTimestamp = order.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                for (TimeRange timeRange : timeRanges) {
                    if(timeRange.getStartTimestamp() <= endTimestamp && timeRange.getStartTimestamp() > startTimestamp) {
                        timeRange.setIsReservation(1);
                    }
                }
            }
            //2 只有startTime 与所需查询日期相同
            if(startTime.equals(date) && !endTime.equals(date)) {
                Long startTimestamp = order.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                Long endTimestamp = LocalDateTime.parse(date + " 24:00:00",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.of("+8")).toEpochMilli();
                for (TimeRange timeRange : timeRanges) {
                    if(timeRange.getStartTimestamp() <= endTimestamp && timeRange.getStartTimestamp() > startTimestamp) {
                        timeRange.setIsReservation(1);
                    }
                }
            }
            //3 只有endTime 与所需查询日期相同
            if(!startTime.equals(date) && endTime.equals(date)) {
                Long startTimestamp = LocalDateTime.parse(date + " 00:00:00",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.of("+8")).toEpochMilli();
                Long endTimestamp = order.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                for (TimeRange timeRange : timeRanges) {
                    if(timeRange.getStartTimestamp() <= endTimestamp && timeRange.getStartTimestamp() > startTimestamp) {
                        timeRange.setIsReservation(1);
                    }
                }
            }
        }
        //将所有时间已过的对象置为2
        Long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        for (TimeRange timeRange : timeRanges) {
            //开始时间小于等于现在
            if(timeRange.getStartTimestamp() <= now) {
                timeRange.setIsReservation(2);
            }
        }
        return timeRanges;
    }
    /**
     * 根据订单装配时间轴，使用TimeUnit
     */
    public static List<Integer> assembly(List<TimeRange> timeRanges) {
        //解析TimeRange
        List<Integer> result = new ArrayList<>();
        for (TimeRange timeRange : timeRanges) {
            result.add(timeRange.getIsReservation());
        }
        return result;
    }

    /**
     * 装配一条初始时间轴
     */
    public static List<TimeRange> getTimeRanges(String date) {
        //一天24小时，半小时为一个分段，总共48个段
        List<TimeRange> timeRanges = new ArrayList<>(48);
        //对应当天的起始值并解析成对应时间戳时间戳
        String startAt = date + " 00:00:00";
        long l = LocalDateTime.parse(startAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        for (int i = 1; i <= 48; i++) {
            timeRanges.add(new TimeRange(l + i * halfHour, 0));
        }
        return timeRanges;
    }
}
