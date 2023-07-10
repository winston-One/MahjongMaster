package com.queshen.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author winston
 * @create 2022/11/26 18:07
 * @Description: Man can conquer nature
 * 登录接口加上：httpSession.setAttribute("userOnlineListener", new UserOnlineListener(commonUserNode.getId()));
 * 监听当前系统在线用户
 */
@Slf4j
public class UserOnlineListener implements HttpSessionBindingListener {

    private String openid;

    public UserOnlineListener(String openid){
        this.openid = openid;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        HttpSession session=event.getSession();
        ServletContext application=session.getServletContext();
        //从application获取当前登录用户列表
        List<String> userOnlineList= (List<String>) application.getAttribute("userOnlineList");
        //如果该属性不存在,则初始化
        if(userOnlineList==null){
            userOnlineList=new ArrayList<>();
            application.setAttribute("userOnlineList",userOnlineList);
        }
        //将当前用户添加至用户列表
        userOnlineList.add(this.openid);
        log.info("session属性绑定=======>{}", this.openid);
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session=event.getSession();
        ServletContext application=session.getServletContext();
        //从application获取当前登录用户列表
        List<Long> userOnlineList= (List<Long>) application.getAttribute("userOnlineList");
        //将该用户从列表中移除
        userOnlineList.remove(this.openid);
    }
}
