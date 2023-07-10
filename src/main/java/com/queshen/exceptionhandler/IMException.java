package com.queshen.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author WinstonYv
 * @create 2023/5/2 20:24
 * @Description: Man can conquer nature
 **/
@Data
@ToString
public class IMException extends Exception{

    private String code;

    private String message;

    public IMException() {
        super();
    }

    public IMException(String message) {
        super(message);
        this.message = message;
    }

    public IMException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public IMException(Throwable cause) {
        super(cause);
    }

    public IMException(String message, Throwable cause) {
        super(message, cause);
    }

    public IMException(String message, Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
