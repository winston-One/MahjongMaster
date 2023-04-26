package com.queshen.utils;

/**
 * Redis相关字段枚举
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public class RedisConstants {

    // Redis中存放登录功能中所有用户的token消息的文件包
    public static final String LOGIN_USER_KEY = "login:token:";

    // 登录有效期30天,根据业务场景自行调整
    public static final Long LOGIN_USER_TTL = 30L;

}
