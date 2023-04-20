package com.queshen.config;

import com.queshen.utils.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor TokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有需要登录才能使用的请求都带上/authority
        registry.addInterceptor(TokenInterceptor).addPathPatterns("/**/authority/**");
    }
}
