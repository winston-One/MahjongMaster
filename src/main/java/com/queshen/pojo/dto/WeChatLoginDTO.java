package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 16:26
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatLoginDTO {

    /**
     * 更新:传入的是openid，
     * 登录:传入的是微信凭证code
     */
    private String code;

    // ("昵称（如果是快捷登录则不传）")
    private String nickname;

    // ("头像url（如果是快捷登录则不传）")
    private String avatarUrl;

}
