package com.queshen.pojo.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
@Data
public class ConsumeResult {

    public Integer code;

    public String msg;

    public JSONArray Data;
}
