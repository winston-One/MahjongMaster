package com.queshen.utils;

import com.queshen.cache.RedisCache;
import com.queshen.pojo.dto.UserDTO;
import com.queshen.pojo.po.User;
import com.queshen.exceptionhandler.LoginException;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 * 用于 token 访问中的拦截
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource(name = "loginCache")
    private RedisCache<User> cache;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)  {
        //TODO 获取请求头中的token信息，如果Redis中存在相关信息，则刷新token有效期，否则放行
        String authority = request.getHeader("Authority");
        if (StringUtils.isEmpty(authority)){
            LoginException loginException = new LoginException();
            loginException.setCode(20001);
            loginException.setMsg("没有携带Authority，路径："+request.getServletPath());
            throw loginException;
        }
        Claims claims = JwtUtils.parse(authority);
        if (claims != null){
            String openid = claims.getSubject(); //判断token是否正常
            Object o = cache.get(openid);
            if (o != null){
                UserHolder.saveUser(new UserDTO(openid,null,null));
                return true;
            }
        }
        LoginException loginException = new LoginException();
        loginException.setCode(20001);
        loginException.setMsg("请先登录验证");
        throw loginException;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        UserHolder.removeUser();
    }
}
