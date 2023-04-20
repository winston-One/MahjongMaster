package com.queshen.pojo.bo;

import lombok.Data;
/**
 * @author winston
 * @create 16:26
 * @Description:
 **/
/**
 * 微信授权登录之后返回的凭证code，可以解析成下面几个参数
 */
@Data
public class WeChatLoginResponse {

    private String openid;

    private String session_key;

    private String unionid;

    private Integer errcode;

    private String errmsg;

}
