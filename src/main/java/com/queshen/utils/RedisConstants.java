package com.queshen.utils;

/**
 * Redis相关字段枚举
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public class RedisConstants {

    public static final String LOGIN_USER_KEY = "login:token:";

    // 登录有效期。30天
    public static final Long LOGIN_USER_TTL = 30L;

}
