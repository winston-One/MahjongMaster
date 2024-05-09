package com.queshen.exceptionhandler;

import lombok.*;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息

}
