package com.queshen.exceptionhandler;

/**
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
