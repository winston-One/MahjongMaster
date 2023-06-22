//package com.queshen.filter;
//
//import com.queshen.exceptionhandler.AuthException;
//import com.queshen.service.IUserService;
//import com.queshen.utils.UserHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author winston
// * @create 2023/4/20 20:38
// * @Description: Man can conquer nature
// * 认证过滤器
// */
//@Component
//public class AuthFilter implements Filter {
//
//    private final IUserService userService;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String requestURI = request.getRequestURI();
//        String openid = UserHolder.getUser().getOpenid();
//        if (StringUtils.isEmpty(openid)) {
//            throw new AuthException("openid为空");
//        }
//        // todo 认证处理
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    public AuthFilter(IUserService userService) {
//        this.userService = userService;
//    }
//}
