package com.queshen.pojo.bo;

import com.queshen.utils.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 返回结果实体类
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Boolean success;
    // 状态码
    private Integer code;

    private String errorMsg;

    private Object data;

    private Long total;

    //private Map<String, Object> data = new HashMap<String, Object>();

    public static Result ok(){
        return new Result(true, ResultCode.SUCCESS,null, null, null);
    }
    public static Result ok(Object data){
        return new Result(true, ResultCode.SUCCESS,null, data, null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(true, ResultCode.SUCCESS,null, data, total);
    }
    public static Result fail(String errorMsg){
        return new Result(false, ResultCode.ERROR, errorMsg, null, null);
    }
//    public Result data(String key, Object value){
//        this.data.put(key, value);
//        return this;
//    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }
}
