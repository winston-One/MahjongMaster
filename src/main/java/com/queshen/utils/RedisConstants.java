package com.queshen.utils;
/**
 * Redis相关字段枚举
 *
 *@author WinstonYv
 *@since 2022/11/10
 */
public class RedisConstants {

    public static final String LOGIN_USER_KEY = "login:token:";
    // 登录有效期。30天
    public static final Long LOGIN_USER_TTL = 30L;

}
