package com.queshen.exceptionhandler;

import lombok.Data;

/**
 * @author winston
 * @create 2022/12/18 11:29
 * @Description: Man can conquer nature
 **/
@Data
public class PayReCallException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息

    public PayReCallException() {
    }

    public PayReCallException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
