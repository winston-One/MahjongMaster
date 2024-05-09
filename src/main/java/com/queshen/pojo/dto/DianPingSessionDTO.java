package com.queshen.pojo.dto;

import lombok.Data;

/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
@Data
public class DianPingSessionDTO {

    //即为session
    private String access_token;

    //过期时间,秒，如10000秒之后过期
    private Long expires_in;

    //session的权限范围，对应模块名称
    private String scope;

    //即为refresh_session，授权可刷新次数用完后，将不再返回新的refresh_session
    private  String refresh_token;

    //剩余刷新次数
    private Integer remain_refresh_count;

    //客户id
    private String bid;

    //放回code
    private  Integer code;

    //提示信息
    private String msg;
}
