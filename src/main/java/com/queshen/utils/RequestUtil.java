package com.queshen.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public class RequestUtil {

    // 将map形式存储的请求参数转换成拼接到url路径上的格式
    public static String mapToGetParam(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
        }
        return sb.toString();
    }
}
