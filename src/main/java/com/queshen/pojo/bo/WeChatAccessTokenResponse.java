package com.queshen.pojo.bo;

import lombok.Data;

/**
 * @author winston
 * @create 17:06
 * @Description:
 **/
@Data
public class WeChatAccessTokenResponse {

    private String access_token;

    // access_token 的过期时间，一般是2个小时，7200秒，存入的是
    private Long expires_in;

    private Integer errcode;

    private String errmsg;

}
