package com.queshen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 用户登录成功之后返回给前端的数据，
 **/
@Data
@AllArgsConstructor
public class UserLoginVo {

    private String Authority;

    // 用户唯一标识,登录之后微信会封装到code变量里
    private String openid;

    private String avatarUrl;

    private String nickname;

    @Override
    public String toString() {
        return "UserLoginVo{" +
                "Authority='" + Authority + '\'' +
                ", openid='" + openid + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
