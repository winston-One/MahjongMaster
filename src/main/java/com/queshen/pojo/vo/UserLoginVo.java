package com.queshen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author winston
 * @create 14:07
 * @Description:
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
