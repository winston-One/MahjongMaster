package com.queshen.utils;

/**
 * @author WinstonYv
 * @create 19:03
 * @Description:
 **/
public interface ResultCode {

    Integer SUCCESS = 20000; //成功

    Integer ERROR = 20001; //失败

    Integer QUICK_LOGIN_ERROR = 20002; //快捷登录失败

    Integer NEED_REFRESH_INFO = 20005; //需要重新获取用户信息，以达到最新

}
