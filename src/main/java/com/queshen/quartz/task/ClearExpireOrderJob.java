package com.queshen.quartz.task;

import cn.hutool.json.JSONUtil;
import com.queshen.pojo.dto.OrderDTO;
import com.queshen.pojo.po.Order;
import com.queshen.service.OrderService;
import com.queshen.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author WinstonYv
 * @create 2022/11/22 23:25
 * @Description: Man can conquer nature
 **/
@Slf4j
public class ClearExpireOrderJob implements Job {

    private final ThreadPoolExecutor executorService = SpringUtil.getBean(ThreadPoolExecutor.class);

    private final StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

    private final OrderService orderService = SpringUtil.getBean(OrderService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        executorService.execute(() -> {
            log.info("订单清理定时任务开始");
            //在redis中查找所有的订单信息
            String key = "*||*";
            Set<String> keysList = stringRedisTemplate.keys(key);
            if (keysList.size() != 0) {
                List<String> strings = stringRedisTemplate.opsForValue().multiGet(keysList);
                // 用于存储订单信息以便加入mysql数据库
                List<Order> expireOrderList = new ArrayList<>();
                // 扫描redis中所有的订单，将过期的删除
                for (String s : strings) {
                    Order order = new Order();
                    OrderDTO orderDTO = JSONUtil.toBean(s, OrderDTO.class);
                    //处理过期订单
                    if (orderDTO.getExpireTime().getTime() < System.currentTimeMillis()) {
                        //设置过期状态
                        BeanUtils.copyProperties(orderDTO, order, "expireTime");
                        order.setStatus(3);// 订单状态为3表示超时仍然为支付，但是依旧存储该记录
                        expireOrderList.add(order);
                        stringRedisTemplate.delete(orderDTO.getUserId() + "||" + orderDTO.getId());
                    }
                }
                //将过期信息加入mysql
                if (!expireOrderList.isEmpty()) {
                    orderService.updateBatchById(expireOrderList);
                }
            }
        });
    }
}
