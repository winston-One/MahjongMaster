package com.queshen.constant;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 全局封装请求返回状态码信息
 **/
public interface ResultCode {

    Integer SUCCESS = 20000; //成功

    Integer ERROR = 20001; //失败

    Integer IM_RECEIVE_FAIL = 666; // 即时通讯异常，服务端处理完消息之后，接收端接收异常
}
