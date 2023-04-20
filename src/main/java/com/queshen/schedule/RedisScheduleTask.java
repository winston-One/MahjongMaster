package com.queshen.schedule;

import cn.hutool.json.JSONUtil;
import com.queshen.pojo.dto.OrderDTO;
import com.queshen.pojo.po.Order;
import com.queshen.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Log4j2
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启异步
public class RedisScheduleTask {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    OrderService orderService;

    /**
     * 定时删除redis中过期订单，每五分钟进行一次
     */
    @Async
    @Scheduled(fixedDelay = 1000*5*60)  //间隔1秒 300000
    public void deleteExpireOrder() {
        log.info("定时任务开始");
        //在redis中查找所有的订单信息
        String key = "*||*";
        Set<String> keysList = stringRedisTemplate.keys(key);
        if (keysList.size() != 0) {
            List<String> strings = stringRedisTemplate.opsForValue().multiGet(keysList);
            //用于存储订单信息以便加入mysql数据库
            List<Order> expireOrderList = new ArrayList<>();
            //扫描redis中所有的订单，将过期的删除
            for (String s : strings) {
                Order order = new Order();
                OrderDTO orderDTO = JSONUtil.toBean(s, OrderDTO.class);
                //处理过期订单
                if (orderDTO.getExpireTime().getTime() < System.currentTimeMillis()) {
                    //设置过期状态
                    BeanUtils.copyProperties(orderDTO, order, "expireTime");
                    order.setStatus(3);
                    expireOrderList.add(order);
                    stringRedisTemplate.delete(orderDTO.getUserId() + "||" + orderDTO.getId());
                }
            }
            //将过期信息加入mysql
        if (!expireOrderList.isEmpty()) {
                orderService.updateBatchById(expireOrderList);
            }

        }
        return;
    }
}
