package com.queshen.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queshen.pojo.bo.Message;
import com.queshen.pojo.po.UserConversation;
import com.queshen.service.IUserConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WinstonYv
 * @create 2023/4/24 21:47
 * @Description: Man can conquer nature
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端。
 * 从而可以统计当前在线人数，websocket是长连接，配合前端就能搭建即时通讯功能
 */
@Component
@Slf4j
@Service
@ServerEndpoint("/IM/online/{userID}")
public class WebSocketServer {

    @Autowired
    private IUserConversationService userConversationService;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 存放所有在线的客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userID") String openId) {
        log.info("即时通讯IM建立成功：{}客户端接入成功",openId);
        clients.put(openId, session);
        webSocketSet.add(this);     // 加入set中
        addOnlineCount();           // 在线数加1
        session.getAsyncRemote().sendText(String.valueOf(getUnreadCount(openId)));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userID") String userID) {
        webSocketSet.remove(this);  // 从set中删除
        clients.remove(userID);
        subOnlineCount();              // 在线数减1
        log.info("释放的sid=" + userID + "的客户端");
    }

    /**
     * 收到客户端消息后调用的方法
     * 客户端发送消息的时候：消息中带有接收者的id，服务端就会转发过去，实现了客户端发送消息和服务端接收消息
     * 客户端接收消息的时候：只需要前端的websocket监听好即可
     * @Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("userID") String openId) {
        Message IMMsg = JSON.parseObject(message, Message.class);
        log.info("收到来自客户端 sid=" + IMMsg.getOpenId() + " 的信息,准备发送给rid=" + IMMsg.getReceiveId());
        session.getAsyncRemote().sendText(String.valueOf(getUnreadCount(openId)));
    }

    /**
     * 发生错误回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getBasicRemote() + "客户端发生错误");
    }

    public static boolean online(String openid){
        return clients.containsKey(openid);
    }

    public static Session getSession(String openid){
        return clients.get(openid);
    }
    // 获取的是该用户的所有联系人加起来的所有未读消息

    public int getUnreadCount(String openid){
        List<UserConversation> list = userConversationService.list(
                new QueryWrapper<UserConversation>().eq("openid",openid)
        );
        int unreadCount = 0;
        for (UserConversation userConversation : list){
            unreadCount += userConversation.getUnreadCount();
        }
        return unreadCount;
    }

    /**
     * 获取当前在线人数，以此可以做出当前有多少人正在看该麻将门店
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前在线人数 +1
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 当前在线人数 -1
     */
    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }
}

