package com.queshen.service.impl;

/**
 * @author winston
 * @create 2023/4/24 21:47
 * @Description: Man can conquer nature
 **/

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端。
 */
@Component
@Slf4j
@Service
@ServerEndpoint("/IM/online/{userID}")
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    // private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String userID = "";

    private String touserID="";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userID") String userID) {
        log.info("即时通讯IM建立成功：=={}",userID);
        String receiveID = "";
        log.info(String.valueOf(session.getRequestURI()));
        this.session = session;
        this.userID = userID;
        this.touserID = receiveID;
        log.info("接受者" + receiveID);
        webSocketSet.add(this);     // 加入set中
        addOnlineCount();           // 在线数加1
        try {
            sendMessage("conn_success");
            log.info("有新客户端开始监听,sid=" + userID + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  // 从set中删除
        subOnlineCount();              // 在线数减1
        // 断开连接情况下，更新主板占用情况为释放
        log.info("释放的sid=" + userID + "的客户端");
        releaseResource();
    }

    private void releaseResource() {
        // 这里写释放资源和要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端 sid=" + userID + " 的信息:" + message);
        // 群发消息
        HashSet<String> sids = new HashSet<>();
        if(this.touserID.equals("1")){
            for (WebSocketServer item : webSocketSet) {
                sids.add(item.userID);
            }
        }else {
            log.info("推送到："+this.touserID);
            sids.add(this.touserID);
        }
        try {
            sendMessage(message, sids);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getBasicRemote() + "客户端发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public static void sendMessage(String message, HashSet<String> toSids) throws IOException {
        log.info("推送消息到客户端 " + toSids + "，推送内容:" + message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        //webSocketSet.get();
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给传入的sid，为null则全部推送
                if (toSids.size() <= 0) {
                    item.sendMessage(jsonObject.getString("msg"));
                } else if (toSids.contains(item.userID)) {
                    item.sendMessage(jsonObject.getString("msg"));
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 实现服务器主动推送消息到 指定客户端
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 获取当前在线人数
     *
     * @return
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前在线人数 +1
     *
     * @return
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 当前在线人数 -1
     *
     * @return
     */
    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    /**
     * 获取当前在线客户端对应的WebSocket对象
     *
     * @return
     */
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}

