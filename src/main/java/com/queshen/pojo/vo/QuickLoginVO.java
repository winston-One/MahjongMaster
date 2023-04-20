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

    private String openid;

    private String avatarUrl;

    private String nickname;

    private String Authority;

}
