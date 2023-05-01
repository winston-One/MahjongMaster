package com.queshen.utils;

import com.queshen.cache.RedisCache;
import com.queshen.exceptionhandler.LoginException;
import com.queshen.pojo.dto.UserDTO;
import com.queshen.pojo.po.User;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截类
 * 实现一些需要登录权限的操作
 * @author WinstonYv
 * @since 2022/11/10
 */
//@Slf4j
//@Component  * 如果需要拦截未登录的请求，可以将注释去掉
public class LoginInterceptor implements HandlerInterceptor {

    @Resource(name = "loginCache")
    private RedisCache<User> cache;

    // 获取请求头中的token信息，如果Redis中存在相关信息，则刷新token有效期，否则放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String authority = request.getHeader("Authority");
        // 如果需要认证的请求中，请求头必须携带Authority属性——token信息
        if (StringUtils.isEmpty(authority)){
            LoginException loginException = new LoginException();
            loginException.setCode(20001);
            loginException.setMsg("没有携带Authority，认证失败，路径：" + request.getServletPath());
            throw loginException;
        }
        Claims claims = JwtUtils.parse(authority);
        //判断token是否正确
        if (!StringUtils.isEmpty(claims)){
            String openid = claims.getSubject();
            Object user = cache.get(openid);
            // 判断token虽然正确但是是否已经过期
            if (!StringUtils.isEmpty(user)){
                UserHolder.saveUser(new UserDTO(openid,null,null));
                return true;
            }
        }
        LoginException loginException = new LoginException();
        loginException.setCode(20001);
        loginException.setMsg("请先登录认证");
        throw loginException;
    }

    /**
     * 只有在 preHandle(……) 被成功执行后并且返回 true 才会被执行。
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
