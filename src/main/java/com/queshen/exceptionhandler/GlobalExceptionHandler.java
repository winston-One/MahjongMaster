package com.queshen.exceptionhandler;

import com.queshen.pojo.bo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
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
