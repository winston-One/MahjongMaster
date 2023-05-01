package com.queshen.aspectj;

import com.alibaba.fastjson.JSON;
import com.queshen.anno.Log;
import com.queshen.pojo.dto.UserDTO;
import com.queshen.pojo.po.LogInfo;
import com.queshen.service.ILogInfoService;
import com.queshen.utils.IpUtil;
import com.queshen.utils.ServletUtils;
import com.queshen.utils.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.Collection;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author winston
 * @create 2022/11/18 10:06
 * @Description: Man can conquer nature
 * @Aspect切面注解
 **/
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    ILogInfoService logService;

    /**
     * 处理完请求后执行
     * @param joinPoint 切点,在所有标记了@Log的方法中切入统一进行处理,jsonResult是响应体内容
     */
    @AfterReturning(pointcut = "@annotation(logInfo)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log logInfo, Object jsonResult) {
        handleLog(joinPoint, logInfo, null, jsonResult);
    }

    // 拦截异常操作
    @AfterThrowing(value = "@annotation(logInfo)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log logInfo, Exception e) {
        handleLog(joinPoint, logInfo, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log logInfo, final Exception e, Object jsonResult) {
        try {
            // 获取当前的用户
            UserDTO user = UserHolder.getUser();
            // 数据库日志
            LogInfo operateLog = new LogInfo();
            if (StringUtils.isEmpty(e)){
                operateLog.setStatus(0);
            } else {
                operateLog.setStatus(1);
                operateLog.setErrorMsg(e.getMessage().substring( 0, 2000));
            }
            operateLog.setContent(logInfo.content());
            // ordinal()方法是枚举变量在枚举类中声明的顺序，下标从0开始
            operateLog.setBusinessType(logInfo.businessType().ordinal());
            operateLog.setIp(IpUtil.getIpAddress(ServletUtils.getRequest()));
            operateLog.setUrl(ServletUtils.getRequest().getRequestURI());
            if (user != null) {
                operateLog.setOperatorId(user.getOpenid());
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();// 获取被代理的对象
            String methodName = joinPoint.getSignature().getName();// 获取目标方法名
            operateLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operateLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            handlerLogParams(joinPoint, logInfo, operateLog, jsonResult);
            // 保存数据库
            logService.saveOperateLog(operateLog);
        } catch (Exception error) {
            // 记录本地异常日志
            log.error("异常信息:{}", error.getMessage());
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     */
    public void handlerLogParams(JoinPoint joinPoint, Log logInfo, LogInfo operateLog, Object result) {
        // 是否需要保存response
        if (logInfo.isSaveResponseData() && StringUtils.isEmpty(result)) {
            operateLog.setResult(JSON.toJSONString(result).substring(0, 2000));
        }
        // 是否需要保存request
        if (logInfo.isSaveRequestData()) {
            // 获取请求的参数，放到log中
            String requestMethod = operateLog.getRequestMethod();
            if ("PUT".equals(requestMethod) || "POST".equals(requestMethod)) { // 请求参数放在请求体中
                String params = argsArrayToString(joinPoint.getArgs());// 获取请求参数
                operateLog.setParam(params.substring(0, 2000));// 前2000个字符是
            } else { // 非post和put请求方式 必须从路径中得到请求参数
                Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                operateLog.setParam(paramsMap.toString().substring(0, 2000));// 截取字符前2000个字符
            }
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (!StringUtils.isEmpty(paramsArray) && paramsArray.length > 0) {
            for (Object param : paramsArray) {
                if (StringUtils.isEmpty(param) && isFilterParam(param)) {
                    try {
                        Object jsonParam = JSON.toJSON(param);
                        params += jsonParam.toString() + ",";
                    } catch (Exception e) {
                        log.info("参数拼装 =={}",e.getMessage());
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     * @param param 对象信息。
     * @return 如果是需要过滤的对象，则返回false；否则返回true。
     */
    @SuppressWarnings("rawtypes")// 编译器忽略指定的警告，不用在编译完成后出现警告信息。
    public boolean isFilterParam(final Object param) {
        Class<?> paramClazz = param.getClass();
        if (paramClazz.isArray()) {
            // isAssignableFrom是用native标注的，是底层方法，A.isAssignableFrom(B)
            //确定一个类(B)是不是继承来自于另一个父类(A)，一个接口(A)是不是实现了另外一个接口(B)，或者两个类相同
            return paramClazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }else if (Collection.class.isAssignableFrom(paramClazz)) { // 判断paramClazz是否是集合类型的子类
            Collection collection = (Collection) param;
            for (Object value : collection) {
                return value instanceof MultipartFile;// 如果是文件类型，是不需要过滤的
            }
        }else if (Map.class.isAssignableFrom(paramClazz)) { // 判断paramClazz是否是Map集合类型的子类
            Map map = (Map) param;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;// 如果是文件类型，是不需要过滤的
            }
        }
        return
                !(param instanceof HttpServletRequest ||
                param instanceof HttpServletResponse ||
                param instanceof BindingResult);
    }
}
