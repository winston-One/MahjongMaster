package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author DJT
 * @since 2022-07-31
 */
@TableName("t_user_conversation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("openid")
    private String openId;

    private String receiveId;

    // 会话id
    private String conversationId;

    // 会话聊天中未读的消息数
    private Integer unreadCount;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    public UserConversation(String openId, String receiveId, String conversationId, Integer unreadCount, LocalDateTime updateTime, LocalDateTime createTime) {
        this.openId = openId;
        this.receiveId = receiveId;
        this.conversationId = conversationId;
        this.unreadCount = unreadCount;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }
}
