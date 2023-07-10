package com.queshen.pojo.dto;

import lombok.Data;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 请求美团验券接口之后的返回结果集封装到实体
 **/
@Data
public class PreparemtRecordResultDTO {

    public Integer code;

    public String msg;

    public PreparemtRecordDTO Data;

}
