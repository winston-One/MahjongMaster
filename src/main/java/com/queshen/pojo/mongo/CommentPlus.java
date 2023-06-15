package com.queshen.pojo.mongo;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author WinstonYv
 * @create 2023/4/25 23:35
 * @Description: Man can conquer nature
 * 待开发，后续会植入mongodb
 **/
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Document("comment_plus")
//public class CommentPlus {
//
//    @Id
//    private String id;
//
//    @Field("room_id")
//    private String roomId;
//
//    private String openid;
//
//    @Field("avatar_url")
//    private String avatarUrl;
//
//    private String nickName;
//
//    private String content;
//
//    @Field("like_num")
//    private int likeNum;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    private LocalDateTime creatTime;
//
//    @Field("is_deleted")
//    private int isDeleted;
//
//}
