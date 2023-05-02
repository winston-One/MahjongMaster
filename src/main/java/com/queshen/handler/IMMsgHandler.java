package com.queshen.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.queshen.pojo.bo.Message;
import com.queshen.pojo.po.ChatMsg;
import com.queshen.pojo.po.Conversation;
import com.queshen.pojo.po.UserConversation;
import com.queshen.service.IChatMsgService;
import com.queshen.service.IConversationService;
import com.queshen.service.IUserConversationService;
import com.queshen.service.impl.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class IMMsgHandler {

    private final IChatMsgService chatMsgService;

    private final IUserConversationService userConversationService;

    private final IConversationService conversationService;

    @Transactional(rollbackFor = Exception.class)
    public void handlerGroup(String message, String openid, Session mySession, Map<String, Session> clients) {
        // 处理群消息 TODO
        Session session;
        String otherId;
        for (Map.Entry<String, Session> entry : clients.entrySet()) {
            otherId = entry.getKey();
            session = entry.getValue();
            log.info(otherId + "：" + session);
            // handlerIndividual
            Message IMMsg = JSON.parseObject(message, Message.class);
            IMMsg.setReceiveId(otherId);
            handlerIndividual(JSON.toJSONString(IMMsg), openid, mySession, clients);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void handlerIndividual(String message, String openid, Session mySession, Map<String, Session> clients) {
        boolean isConnectWebSocket = false;
        Integer isUnRead = 0;
        boolean isFirstSend = false;
        String lastMsg;
        Conversation conversation;
        int otherUnreadCount = 0;
        Message IMMsg = JSON.parseObject(message, Message.class);
        String messageId = UUID.randomUUID().toString().trim().replaceAll("-","");
        IMMsg.setId(messageId);
        IMMsg.setContent(message);
        IMMsg.setCreateTime(LocalDateTime.now());
        String receiveId = IMMsg.getReceiveId();
        // 判断对方是否连上websocket，即是否处于聊天框中
        if (clients.containsKey(receiveId)) {
            Session session = clients.get(receiveId);
            session.getAsyncRemote().sendText(JSON.toJSONString(IMMsg));
            isConnectWebSocket = true;
        }
        if (IMMsg.getType() == 1) {
            lastMsg = IMMsg.getContent();
        } else {
            lastMsg = "[图片]";
        }
        // 查询用户会话是否存在，因为一个发送者一个接收者是对应着唯一 一条记录的
        QueryWrapper<UserConversation> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid).eq("receive_id", receiveId);
        UserConversation userConversation = userConversationService.getOne(wrapper);
        // 发送给接收者
        if (userConversation == null) {
            isFirstSend = true;
            // 保存会话消息
            conversation = new Conversation(IMMsg.getType(), lastMsg,LocalDateTime.now(),LocalDateTime.now());
            conversationService.save(conversation);
            //如果对方不在聊天框中，则设置对方未读数为1
            if (!isConnectWebSocket) {
                otherUnreadCount = 1;
                isUnRead = 1;//消息未读
            }
            // 保存用户会话
            userConversation = new UserConversation(openid,receiveId, conversation.getId(), otherUnreadCount, LocalDateTime.now(),LocalDateTime.now());
            userConversationService.save(userConversation);
        } else {
            // 如果之前已经存在过会话，只需要修改对应lastMsg和消息未读数
            conversation = conversationService.getById(userConversation.getConversationId());
            UpdateWrapper<Conversation> conversationWrapper = new UpdateWrapper<>();
            conversationWrapper.eq("id", conversation.getId()).set("last_msg", lastMsg);
            conversationService.update(new Conversation(), conversationWrapper);
            // 如果对方不在聊天框中，则设置对方未读数加1,如果对方在聊天框中就不需要更改数据库信息
            if (!isConnectWebSocket) {
                if (userConversation.getUnreadCount() > 0) { // 存在未读私信
                    isUnRead = 1;// 消息未读
                }
                // userConversation不为空，直接用
                UpdateWrapper<UserConversation> userConversationUpdateWrapper = new UpdateWrapper<>();
                userConversationUpdateWrapper.eq("id", userConversation.getId())
                        .set("unread_count", userConversation.getUnreadCount() + 1);
                userConversationService.update(userConversationUpdateWrapper);
            }
        }
        ChatMsg msg = new ChatMsg(openid, receiveId, IMMsg.getContent(), isUnRead, LocalDateTime.now(), LocalDateTime.now());
        msg.setId(messageId);
        chatMsgService.save(msg);
        //给自己也发消息，保证自己的聊天框也要展示自己的消息
        mySession.getAsyncRemote().sendText(JSON.toJSONString(IMMsg));

        try {
            // 获取是否在线，但是并没有进入聊天框
            boolean isOnline = WebSocketServer.online(receiveId);
            //如果对方不在线 且第一次和对方聊天，且之前不存在未读私信,则发送模板推送
            if (isFirstSend && !isOnline && isUnRead == 1) {
                // rabbitMQ推送消息————你有新的客户，或新的门店给你推荐新的优惠活动
            } else if (!isFirstSend && !isOnline && isUnRead == 1) {
                // rabbitMQ推送消息————收到门店或者客户信息，并且有userConversation.getUnreadCount()条信息未读

            } else if (isOnline && !isConnectWebSocket) {// 如果对方在线，但是没有在聊天框中，就是进入了小程序而已，就不需要MQ
                Session session = WebSocketServer.getSession(receiveId);
                if (!StringUtils.isEmpty(session)) {
                    // 相当于在前端设置红点来提醒用户
                    session.getAsyncRemote().sendText("1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IMMsgHandler(IChatMsgService chatMsgService,IUserConversationService userConversationService,IConversationService conversationService) {
        this.chatMsgService = chatMsgService;
        this.userConversationService = userConversationService;
        this.conversationService = conversationService;
    }
}
