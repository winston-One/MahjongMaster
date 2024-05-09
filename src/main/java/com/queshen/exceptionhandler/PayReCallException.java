package com.queshen.exceptionhandler;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WinstonYv
 * @create 2023/5/10 10:28
 * @Description: Man can conquer nature
 */
@Setter
@Getter
public class PayReCallException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息
}
