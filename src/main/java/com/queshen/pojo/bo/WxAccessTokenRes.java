package com.queshen.pojo.bo;

import lombok.Data;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class WxAccessTokenRes {

    private String access_token;

    private Long expires_in;

    private Integer errcode;

    private String errmsg;
}
