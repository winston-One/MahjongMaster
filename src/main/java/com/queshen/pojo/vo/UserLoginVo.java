package com.queshen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
public class UserLoginVo {

    private String Authority;

    private String openid;

    private String avatarUrl;

    private String nickname;

    //private String phone;
}
