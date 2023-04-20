package com.queshen.utils;

import com.queshen.pojo.dto.UserDTO;

/**
 * 记录当前线程执行的用户 的线程类
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 */
public class UserHolder {

    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }

}
