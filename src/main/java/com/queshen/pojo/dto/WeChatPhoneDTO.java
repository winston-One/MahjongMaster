package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 20:22
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatPhoneDTO {

    // 微信允许获取电话的凭证
    private String code;

    private String openid;

}
