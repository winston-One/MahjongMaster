package com.queshen.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Order;
import com.queshen.pojo.po.Room;
import com.queshen.service.IRoomService;
import com.queshen.service.OrderService;
import com.queshen.service.ReservationInfoService;
import com.queshen.utils.TimeRange;
import com.queshen.utils.TimeRangeUtil;
import com.queshen.pojo.vo.ReservationInfo;
import com.queshen.pojo.vo.RoomInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.queshen.utils.OrderStatus.ORDER_NOT_PAID;
import static com.queshen.utils.TimeRangeUtil.halfHour;


/**
 * @author VioletQin
 * @since 2022/11/21
 */
@Service
@Slf4j
public class ReservationInfoServiceImpl implements ReservationInfoService {

    @Resource
    private IRoomService roomService;

    @Resource
    private OrderService orderService;

    @Override
    public Result getAllInfo(String storeId, String date) {
        //1、根据storeId返回对应店铺的所有房间信息
        List<Room> rooms = roomService.list(new LambdaQueryWrapper<Room>().eq(Room::getStoreId, storeId));
        List<RoomInfoVO> results = new ArrayList<>(rooms.size());

        //2、遍历房间信息，寻找对应订单信息
        for (Room room : rooms) {
            //2.1通过日期查找当天订单和次日
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            //2.2添加过滤条件
            wrapper.eq(Order::getRoomId, room.getRoomId())
                    .ne(Order::getStatus, ORDER_NOT_PAID)
                    .and(wp -> wp.likeRight(Order::getStartTime, date).or().likeRight(Order::getEndTime, date));

            List<Order> orders = orderService.list(wrapper);
            //2.3订单信息拆分归纳
            List<Integer> assembly = TimeRangeUtil.assembly(TimeRangeUtil.timeRanges(orders, date));

            //2.4该房间在当天是否有空闲
            Boolean isFree = false;
            for (Integer integer : assembly) {
                if(integer == 0) {
                    isFree = true;
                    break;
                }
            }
            //2.5信息打理好之后封装RoomInfoVO信息
            RoomInfoVO build = RoomInfoVO.builder()
                    .pricePerHour(room.getPrice())
                    .photoAdd(room.getImage())
                    .roomName(room.getRoomName())
                    .tag(StrUtil.split(room.getRemarks(), "/"))
                    .roomId(room.getRoomId())
                    .timeRanges(assembly)
                    .isFree(isFree)
                    .build();

            results.add(build);
        }
        return Result.ok(results);
    }

    @Override
    public Result getInfoByRoom(String roomId, String date) {
        //1通过日期查找当天订单和次日
        LambdaQueryWrapper<Order> wrapper1 = new LambdaQueryWrapper<>();
        //2添加过滤条件
        wrapper1.eq(Order::getRoomId, roomId)
                .ne(Order::getStatus, ORDER_NOT_PAID)
                .and(wp -> wp.likeRight(Order::getStartTime, date).or().likeRight(Order::getEndTime, date));
        List<Order> orders1 = orderService.list(wrapper1);

        //获取明日时段
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter aFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        long l = LocalDateTime.parse(date + " 00:00:00", aFormat).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //24小时，一小时60分钟，一分钟60秒，一秒60毫秒
        l = l + 24 * 60 * 60 * 1000;
        String nextDate = dateFormat.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneOffset.of("+8")));

        //1通过日期查找当天订单和次日
        LambdaQueryWrapper<Order> wrapper2 = new LambdaQueryWrapper<>();
        //2添加过滤条件
        wrapper2.eq(Order::getRoomId, roomId)
                .ne(Order::getStatus, ORDER_NOT_PAID)
                .and(wp -> wp.likeRight(Order::getStartTime, nextDate).or().likeRight(Order::getEndTime, nextDate));
        List<Order> orders2 = orderService.list(wrapper2);

        //获取时间段
        List<TimeRange> timeRanges1 = TimeRangeUtil.timeRanges(orders1, date);
        List<TimeRange> timeRanges2 = TimeRangeUtil.timeRanges(orders2, nextDate);

        //装配信息
        List<ReservationInfo> results = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        //先装配timeRanges1
        for (TimeRange timeRange : timeRanges1) {
            if (timeRange.getIsReservation() != 2) {
                //将时间戳转换为对应时间格式，仅使用这一次，所以不做工具类
                String start = dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeRange.getStartTimestamp() - halfHour), ZoneOffset.of("+8")));
                String end = dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeRange.getStartTimestamp()), ZoneOffset.of("+8")));

                ReservationInfo build = ReservationInfo.builder()
                        .isNextDay(0)
                        .isFree(timeRange.getIsReservation())
                        .time(start + "-" + end)
                        .build();

                results.add(build);
            }
        }
        //后装配timeRanges2
        for (TimeRange timeRange : timeRanges2) {
            if (timeRange.getIsReservation() != 2) {
                //如果大小不达标，则继续检索
                if(results.size() <= 48) {
                    //将时间戳转换为对应时间格式，仅使用这一次，所以不做工具类
                    String start = dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeRange.getStartTimestamp() - halfHour), ZoneOffset.of("+8")));
                    String end = dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeRange.getStartTimestamp()), ZoneOffset.of("+8")));

                    ReservationInfo build = ReservationInfo.builder()
                            .isNextDay(1)
                            .isFree(timeRange.getIsReservation())
                            .time(start + "-" + end)
                            .build();

                    results.add(build);
                } else {
                    break;
                }
            }
        }

        return Result.ok(results);
    }


/*    public Result all(Long storeId) {
        //1、根据storeId返回对应店铺的所有房间信息
        List<Room> rooms = roomService.list(new LambdaQueryWrapper<Room>().eq(Room::getStoreId, storeId));
        List<RoomInfoVO> results = new ArrayList<>(rooms.size());
        //创建日期格式转换器
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeRangeFormat = DateTimeFormatter.ofPattern("HH:mm");

        //2、遍历房间信息
        for (Room room : rooms) {
            //2、1通过房间号找到对应房间号未来七天的全部订单
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getRoomId, room.getRoomId())
                    .ne(Order::getStatus, ORDER_NOT_PAID)
                    .ge(Order::getStartTime, dateFormat.format(LocalDateTime.now()));
            List<Order> orders = orderService.list(wrapper);
            //2、2订单信息拆分，归纳，封装为DataInfo,当天到次日的订单记得分开！
            Map<String, List<TimeRange>> map = new HashMap<>();
            for (Order order : orders) {
                String startDate = dateFormat.format(order.getStartTime());
                String endDate = dateFormat.format(order.getStartTime());
                //若开始日期和结束日期相同，则执行如下逻辑
                if(startDate.equals(endDate)) {
                    //如果没有对应key，则添加时间信息
                    if(!map.containsKey(startDate)) {
                        map.put(startDate, new ArrayList<>());
                    }
                    map.get(startDate).add(new TimeRange(timeRangeFormat.format(order.getStartTime()),
                                                         timeRangeFormat.format(order.getEndTime())));
                } else {
                    //若开始日期和结束日期不同，则表示两个不同天次
                    if(!map.containsKey(startDate)) {
                        map.put(startDate, new ArrayList<>());
                    }
                    if(!map.containsKey(endDate)) {
                        map.put(endDate, new ArrayList<>());
                    }
                    map.get(startDate).add(new TimeRange(timeRangeFormat.format(order.getStartTime()), "24:00"));
                    map.get(endDate).add(new TimeRange("00:00", timeRangeFormat.format(order.getEndTime())));
                }
            }
            //2、3信息打理好之后封装RoomInfoVO信息
            List<DateInfo> dateInfos = new ArrayList<>();
            for (String s : map.keySet()) {
                DateInfo dateInfo = new DateInfo();
                dateInfo.setDate(s);
                dateInfo.setTimeRanges(map.get(s));
                dateInfos.add(dateInfo);
            }

            //2、4results中添加对应信息
            results.add(new RoomInfoVO(room, dateInfos));
        }
        //3返回List<RoomInfoVo>，储存的信息是，对应店铺内，所有房间，七天内的所有时间相关的预定信息
        return Result.ok(results);
    }*/
}
