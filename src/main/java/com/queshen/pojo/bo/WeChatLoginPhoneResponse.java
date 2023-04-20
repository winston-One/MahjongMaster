package com.queshen.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 16:55
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatLoginPhoneResponse {

    private PhoneInfo phone_info;

    private Integer errcode;

    private String errmsg;

}
