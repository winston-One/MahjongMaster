package com.queshen.controller;

import com.queshen.handler.IMMsgHandler;
import com.queshen.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 * 弹出聊天框之后才是连接上websocket
 * 接收者id是封装到消息体里的
 * /IM/chat/{sendId}/{isGroup}
 */
@ServerEndpoint("/IM/chat/{sendId}/{isGroup}")
@Component
@Slf4j
public class WebSocketChatController {

    /**
     * 存放所有在线的客户端 ConcurrentHashMap是线程安全的
     */
    private static final Map<String, Session> clientList = new ConcurrentHashMap<>();

    // 群消息
    private static final Integer GROUP_MESSAGE = 1;

    // 个人消息
    private static final Integer INDIVIDUAL_MESSAGE = 0;

    @OnOpen
    public void onOpen(Session session, @PathParam("sendId") String sendId) {
        //将新用户存入在线的组
        log.info("有新的客户端连接了: {}", sendId);
        clientList.put(sendId, session);
    }

    /**
     * 收到客户端发来消息,data就是前端发送过来的消息体，后端使用String接收，所以使用JSON解析
     * 发送消息是否成功可以在前端确认，但是在服务端处理完消息给接收端的时候，可能丢失消息，所以保证不丢失消息
     */
    @OnMessage
    public void onMessage(String message,
                          @PathParam("sendId") String sendId,
                          @PathParam("isGroup") Integer isGroup,
                          Session mySession) {
        log.info("服务端收到客户端发来的消息: {}", message);
        IMMsgHandler MessageHandler = SpringUtils.getBean(IMMsgHandler.class);
        if (isGroup == INDIVIDUAL_MESSAGE) {
            MessageHandler.handlerIndividual(message,sendId,mySession,clientList);// 处理个人消息
        }
        if (isGroup == GROUP_MESSAGE) {
            MessageHandler.handlerGroup(message,sendId,mySession,clientList);// 处理群消息
        }
    }

    /**
     * 客户端关闭
     */
    @OnClose
    public void onClose(@PathParam("sendId") String sendId) {
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
