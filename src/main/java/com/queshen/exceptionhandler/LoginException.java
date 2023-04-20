package com.queshen.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 17:26
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息

}
