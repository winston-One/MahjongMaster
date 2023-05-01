package com.queshen.anno;

import com.queshen.constant.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义注解，AOP思想记录操作日志
 *
 * @author winston
 * @create 2022/11/22 0:21
 * @Description: Man can conquer nature
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作详情
     */
    String content() default "";

    /**
     * 操作功能
     */
    BusinessType businessType();

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

}
