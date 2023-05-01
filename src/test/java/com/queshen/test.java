package com.queshen;

import com.queshen.mapper.OrderMapper;
import com.queshen.pojo.bo.TimeRange;
import com.queshen.utils.TimeRangeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author WinstonYv
 * @create 23:25
 * @Description:
 **/

@RunWith(SpringRunner.class)
// 配置websocket，否则报错Error creating bean with name 'serverEndpointExporter' defined in class path
// 出现这个错的原因是在部署项目的时候,项目中含有websocket的@ServerEndpoint注解的时候,
// 如果项目是springboot项目,去除内置tomcat的时候会把websocket的包也给删除掉,所以需要手动在测试类加上这个包，保证测试环境可以使用
@SpringBootTest(classes = MainApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {

    @Autowired
    OrderMapper orderMapper;


    @org.junit.Test
    public void testA() throws ParseException {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = dateFormat1.parse("2023-05-01 13:00:00");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date end = dateFormat2.parse("2023-05-01 14:00:00");
        LocalDateTime startTime = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());
        //判断当前下单的预约时间是否已经不能预约了
        Integer isReserve = orderMapper.isExistReserveTime(startTime,endTime).size();
        System.out.println(orderMapper.isExistReserveTime(startTime,endTime));
        System.out.println(isReserve);
        System.out.println(isReserve>0);
    }

    @Test
    public void test2() throws ParseException {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = dateFormat1.parse("2023-05-01 13:00:00");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date end = dateFormat2.parse("2023-05-01 18:00:00");
        LocalDateTime startTime = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());
        //判断当前下单的预约时间是否已经不能预约了
        Integer isReserve = orderMapper.isExistReserveTime(startTime,endTime).size();
        System.out.println(orderMapper.isExistReserveTime(startTime,endTime));
        System.out.println(isReserve);
        System.out.println(isReserve>0);
    }

    @Test
    public void testUmsAdmin() {
        List<TimeRange> timeRanges = TimeRangeUtil.getTimeRanges("2022-12-16");
        for (TimeRange timeRange : timeRanges) {
            System.out.println(timeRange);
        }
    }

}
