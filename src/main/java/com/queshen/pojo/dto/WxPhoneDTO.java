package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 获取微信登录后的电话号码，服务端根据微信API获得的code和用户唯一标识openid就能解析出里面的电话号码
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPhoneDTO {

    // 微信允许获取电话的凭证
    private String code;

    private String openid;

}
