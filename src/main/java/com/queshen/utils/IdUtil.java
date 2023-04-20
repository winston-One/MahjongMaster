package com.queshen.utils;

import org.springframework.stereotype.Component;

/**
 * @author WinstonYv
 * @create 2022/11/14
 * @Description:
 **/
@Component
public class IdUtil {

    //生成订单id工具类，当前用户id[1-5]+时间戳+queshen
    public String getOrderId(String userId){
        long  l= System.currentTimeMillis();
        String currentTime = String.valueOf(l);
        String subOpenid = userId.substring(1, 5);
        return subOpenid+currentTime+"queshen";
    }

}
