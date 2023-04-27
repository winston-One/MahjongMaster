package com.queshen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/IM/chat/{sendId}/{recvId}")
@Component
@Slf4j
public class WebSocketChatController {

    /**
     * 存放所有在线的客户端 ConcurrentHashMap是线程安全的
     */
    private static final Map<String, Session> clientList = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("sendId") String sendId) {
        //将新用户存入在线的组
        log.info("有新的客户端连接了: {}", sendId);
        clientList.put(sendId, session);
    }

    /**
     * 收到客户端发来消息
     */
    @OnMessage
    public void onMessage(String message,
                          @PathParam("sendId") String sendId,
                          Session mySession) {
        log.info("服务端收到客户端发来的消息: {}", message);
        // todo,通过spring的bean工厂获取消息处理类，处理消息，例如存入数据库，让接收者看到消息
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session,
                        @PathParam("sendId") String sendId,
                        @PathParam("recvId") String recvId) {
        log.info("有用户断开了, openid为:{}", sendId);
        clientList.remove(sendId);
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
