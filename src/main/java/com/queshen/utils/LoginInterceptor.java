package com.queshen.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截类
 * 实现一些需要登录权限的操作
 * @author WinstonYv
 * @since 2022/11/10
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO 判断是否需要拦截该请求
        return true;
    }
}
