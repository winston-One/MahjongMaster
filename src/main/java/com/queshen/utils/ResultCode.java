package com.queshen.utils;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public interface ResultCode {

    Integer SUCCESS = 20000; //成功

    Integer ERROR = 20001; //失败

    Integer QUICK_LOGIN_ERROR = 20002; //快捷登录失败

    Integer NEED_REFRESH_INFO = 20005; //需要重新获取用户信息，以达到最新

}
