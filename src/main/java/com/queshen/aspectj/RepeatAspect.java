package com.queshen.aspectj;

import com.queshen.anno.RepeatRequest;
import com.queshen.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author winston
 * @create 2023/3/17 23:18
 * @Description: Man can conquer nature
 * 防止重复请求的AOP
 **/
@Component
@Aspect
@Slf4j
@Order(0)// 值越小，优先级越高，优先级就是IOC容器的执行顺序
public class RepeatAspect {

    private static final String SUFFIX = "REPEAT_:";

    @Autowired
    private RedisTemplate redisTemplate;

    // 定义注解类型的切点
    @Pointcut("@annotation(com.queshen.anno.RepeatRequest)")
    public void pointcut() {}

    // 实现过滤重复请求功能
    @Around("pointcut()")
    public Object handlerRepeat (ProceedingJoinPoint joinPoint) {
        // 获取 redis key，由 session ID 和 请求URI 构成
        ServletRequestAttributes req = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = req.getRequest();
        String key = SUFFIX + request.getSession().getId() + "_" + request.getRequestURI() + "_" + UserHolder.getUser().getOpenid();

        // 获取方法的 AvoidRepeatRequest 注解
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RepeatRequest annotation = method.getAnnotation(RepeatRequest.class);

        // 判断是否是重复的请求，对于key值相同的数据，设置不成功就是重复请求
        if (!redisTemplate.opsForValue().setIfAbsent(key, 1, annotation.intervalTime(), TimeUnit.SECONDS)) {
            // 已发起过请求
            log.info(annotation.msg());
            return annotation.msg();
        }
        try {
            return joinPoint.proceed();// 非重复请求，执行后续业务代码
        } catch (Throwable e) {
            log.info("防重操作后，后续业务代码执行异常=={}", e.getMessage());
            return "error";
        }
    }
}

