package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author WinstonYv
 * @create 2022/11/22 20:05
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)//通过对象进行链式调用，set方法和get方法等等
@TableName("t_log_info")
public class LogInfo {

    private static final long serialVersionUID = 1L;

    // 日志主键
    private Long id;

    // 操作的功能模块
    private String content;

    // 业务类型
    private Integer businessType;

    // 获取是哪个controller方法的日志
    private String method;

    // 请求方式，GET，POST，PUT，DELETE
    private String requestMethod;

    // 操作人员
    private String operatorId;

    // 请求url
    private String url;

    // 操作地址
    private String ip;

    // 请求参数
    private String param;

    // 返回参数
    private String result;

    // 操作状态（0正常 1异常）
    private Integer status;

    // 错误消息
    private String errorMsg;

    // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

}
