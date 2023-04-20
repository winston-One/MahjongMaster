package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatPhoneDTO {

    // 微信允许获取电话的凭证
    private String code;

    private String openid;

}
