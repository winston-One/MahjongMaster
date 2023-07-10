package com.queshen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationVO {

    private String sendId;

    private String receiveId;

    private String conversationId;

    private String unreadCount;

    private String receiveNickname;

    private String receiveImg;

    private String lastMsg;

    private LocalDateTime updateTime;

}
