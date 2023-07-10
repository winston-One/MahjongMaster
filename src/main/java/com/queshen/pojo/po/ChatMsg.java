package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author WinstonYv
 * @create 2023/5/2 11:52
 * @Description: Man can conquer nature
 * 存放所有的消息
 **/
@Data
@TableName("t_chat_msg")
@Setter
@Getter
@ToString
public class ChatMsg {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String content;

    // 1是文字，2是图片
    private Integer type;

    @TableField("openid")
    private String openId;

    private String receiveId;

    private String conversationId;

    private Integer isUnread;

    @TableLogic
    private Integer isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public ChatMsg(String openid, String receiveId, String content,Integer isUnread, LocalDateTime createTime, LocalDateTime updateTime) {
        this.openId = openid;
        this.receiveId = receiveId;
        this.content = content;
        this.isUnread = isUnread;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
