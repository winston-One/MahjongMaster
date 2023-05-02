package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "openid", type = IdType.ASSIGN_ID)
    private String openid;

    private String nickname;

    private String avatarUrl;

    //用户统一标识符(跨公众号和小程序)
    private String unionId;

    private String phone;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @TableLogic
    private Integer isDelete;

}
