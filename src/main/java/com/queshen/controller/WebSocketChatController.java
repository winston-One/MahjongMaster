package com.queshen.controller;

import com.alibaba.fastjson.JSON;
import com.queshen.handler.IMMsgHandler;
import com.queshen.pojo.bo.Message;
import com.queshen.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
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
@ServerEndpoint("/IM/chat/{sendId}")
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
     * 收到客户端发来消息,data就是前端发送过来的消息体，后端使用String接收，所以使用JSON解析
     * data = {
     *   openId: 4234234234,
     * 	 receiveId: 23423423,
     * 	 type: 1,
     * 	 content: winstonYv
     * }
     */
    @OnMessage
    public void onMessage(String message,
                          @PathParam("sendId") String sendId,
                          Session mySession) {
        log.info("服务端收到客户端发来的消息: {}", message);
        // todo,通过spring的bean工厂获取消息处理类，处理消息，例如存入数据库，让接收者看到消息
        IMMsgHandler MessageHandler = SpringUtils.getBean(IMMsgHandler.class);
        MessageHandler.handlerIndividual(message,sendId,mySession,clientList);// 处理个人消息
        // 还可以处理群消息 handlerGroup 通过连接websocket路径上的参数获取是否是群消息
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
