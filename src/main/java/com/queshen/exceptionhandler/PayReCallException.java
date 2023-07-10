package com.queshen.exceptionhandler;

import lombok.Data;

/**
 * @author WinstonYv
 * @create 2023/5/10 10:28
 * @Description: Man can conquer nature
 */
@Data
public class PayReCallException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息

}
