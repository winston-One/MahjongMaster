package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.po.Order;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 判断当前预约时间是否冲突
     * @param startTime
     * @param endTime
     * @return
     * 订单状态为3表示超时还是未支付，默认取消了订单，但是MySQL仍然会记录着这条订单数据
     */
    List<String> isExistReserveTime(LocalDateTime startTime, LocalDateTime endTime);

}
