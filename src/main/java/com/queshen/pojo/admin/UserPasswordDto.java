package com.queshen.pojo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author WinstonYv
 * @create 2024/3/6 23:52
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPasswordDto {

    private String userNo;

    private String password;

    private String newPassword;
}
