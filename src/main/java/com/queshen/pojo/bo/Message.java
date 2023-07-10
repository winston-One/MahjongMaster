package com.queshen.pojo.bo;

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
public class Message {

    private String id;

    private String openId;

    private String receiveId;

    // 消息内容，如果是图片就是http数据
    private String content;

    // 1是文字，2是图片
    private Integer type;

    private LocalDateTime createTime;
}
