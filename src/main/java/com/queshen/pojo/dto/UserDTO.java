package com.queshen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author VioletQin
 * @since 2022/11/12
 */
@Data
@AllArgsConstructor
public class UserDTO {
    private String openid;
    private String nickname;
    private String avatarUrl;
}
