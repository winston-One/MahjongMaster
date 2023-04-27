package com.queshen.pojo.bo;

import lombok.Data;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class WxAccessTokenRes {

    private String access_token;

    // access_token 的过期时间，一般是2个小时，7200秒，存入的是
    private Long expires_in;

    private Integer errcode;

    private String errmsg;

}
