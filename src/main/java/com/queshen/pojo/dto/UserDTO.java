package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
public class UserDTO {

    private String openid;

    private String nickname;

    private String avatarUrl;

}
