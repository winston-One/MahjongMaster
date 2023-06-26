package com.queshen.constant;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 全局封装请求返回状态码信息
 **/
public interface ResultCode {

    Integer SUCCESS = 20000; //成功

    Integer ERROR = 20001; //失败

    Integer QUICK_LOGIN_ERROR = 20002; //快捷登录失败

    Integer NEED_REFRESH_INFO = 20005; //需要重新获取用户信息，以达到最新

    Integer IM_RECEIVE_FAIL = 666; // 即时通讯异常，服务端处理完消息之后，接收端接收异常

}
