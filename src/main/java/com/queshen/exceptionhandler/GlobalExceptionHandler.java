package com.queshen.exceptionhandler;

import com.queshen.pojo.bo.Result;
import com.queshen.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定所需要出现的异常，然后执行该方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(IMException.class)
    @ResponseBody
    public Result doBusinessException(Exception e) {
        log.info(e.getMessage());
        return Result.fail("即时通讯消息处理异常，客户端无法接收").code(ResultCode.IM_RECEIVE_FAIL);
    }

    //出现算术运算异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.fail("执行了ArithmeticException异常处理..");
    }

    //自定义异常
    @ExceptionHandler(LoginException.class)
    @ResponseBody //为了返回数据
    public Result error(LoginException e) {
        log.error(e.getMsg());
        return Result.fail(e.getMsg()).code(e.getCode());
    }

}
