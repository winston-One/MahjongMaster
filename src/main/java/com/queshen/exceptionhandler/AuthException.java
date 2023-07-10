package com.queshen.exceptionhandler;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 无门店管理员权限异常类
 */
public class AuthException extends RuntimeException{

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }
}
