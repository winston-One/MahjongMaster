package com.queshen.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 19:50
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatLoginPhoneRequest {

    private String access_token;

    private String code;

}
