package com.queshen.pojo.bo;

import lombok.Data;
/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 微信授权登录之后返回的凭证code，可以将code解析成下面几个参数
 **/
@Data
public class WeChatLoginResponse {

    private String openid;

    private String session_key;

    private String unionid;

    private Integer errcode;

    private String errmsg;

}
