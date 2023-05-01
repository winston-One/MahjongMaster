package com.queshen.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author winston
 * @create 2023/3/17 23:04
 * @Description: Man can conquer nature
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RepeatRequest {

    // 请求次数，默认是1秒
    int intervalTime() default 3;

    // 返回的提示信息
    String msg() default "限定时间内勿重复请求！保持幂等性OK？";

}
