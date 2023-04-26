package com.queshen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author winston
 * @create 2022/12/24 10:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
public class QuickLoginVO {

    // openid是微信小程序登录的用户标识
    private String openid;

    // 头像
    private String avatarUrl;

    // code解析出的微信昵称
    private String nickname;

    // 通过openid设置的token
    private String Authority;

}
