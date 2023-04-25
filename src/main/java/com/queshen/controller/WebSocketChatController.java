package com.queshen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/IM/chat/{myOpenid}")
@Component
@Slf4j
public class WebSocketChatController {

    /**
     * 存放所有在线的客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("myOpenid") String myOpenid) {
        //将新用户存入在线的组
        log.info("有新的客户端连接了: {}", myOpenid);
        clients.put(myOpenid, session);
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session, @PathParam("myOpenid") String myOpenid) {
        log.info("有用户断开了, openid为:{}", myOpenid);
        clients.remove(myOpenid);
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message,
                          @PathParam("myOpenid") String myOpenid,
                          Session mySession) {
        log.info("服务端收到客户端发来的消息: {}", message);
//        OnMessageHandler handler = SpringUtils.getBean(OnMessageHandler.class);
//        handler.handle(message,myOpenid,getType,university,mySession,clients);
    }
}
