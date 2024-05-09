package com.queshen.exceptionhandler;

import lombok.*;

/**
 * @author WinstonYv
 * @create 2023/5/2 20:24
 * @Description: Man can conquer nature
 **/
@ToString
@Getter
@Setter
public class IMException extends Exception{

    private String code;

    private String message;
}
