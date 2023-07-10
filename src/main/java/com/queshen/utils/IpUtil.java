package com.queshen.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author WinstonYv
 * @create 2022/11/18 10:06
 * @Description: Man can conquer nature
 *
 * 获取用户真实IP地址，如果使用request.getRemoteAddr(),有可能用户使用了代理软件方式避免真实IP地址
 * 如果黑客通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，取X-Forwarded-For中第一个非unknown的有效IP字符串才是真实IP地址。
 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,192.168.1.100
 * 用户真实IP为： 192.168.1.110
 */
@Slf4j
public class IpUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String UNKNOWN = "unknown";

    public static String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        // 如果请求头里获取不到ip
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            // 如果Proxy-Client-IP的值还是获取不到ip
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            // 如果WL-Proxy-Client-IP的值还是获取不到ip
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            // 如果HTTP_CLIENT_IP的值还是获取不到ip
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            // 如果HTTP_X_FORWARDED_FOR的值还是获取不到ip
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            // 如果X-Real-IP的值还是获取不到ip
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                // 一开始已经先从请求头获取了，所以获取不到的原因就不是用户使用了代理软件方式
                ip = request.getRemoteAddr();
            }
        } else {
            // 根据逗号进行划分出多个ip地址
            String[] ipLists = ip.split(",");
            for (String ipItem : ipLists) {
                if (!(UNKNOWN.equalsIgnoreCase(ipItem))) {
                    ip = ipItem;
                    break;
                }
            }
        }
        return ip;
    }

    // 是否发生IP洪水攻击,3秒内的同一个ip请求数量不超过5次，可以自定义设置
    public Boolean isFloodAttack(HttpServletRequest request){

        String ip = this.getIpAddress(request);
        // IP:——在redis中建立一层IP文件夹
        if(stringRedisTemplate.hasKey("IP:" + ip)){
            stringRedisTemplate.opsForValue().increment("IP:" + ip);
            if(Integer.parseInt(stringRedisTemplate.opsForValue().get("IP:" + ip)) >= 5){
                log.info("IP访问次数过多");
                return false;
            }
        }else {
            stringRedisTemplate.opsForValue().set("IP:" + ip, "1");
            stringRedisTemplate.expire("IP:" + ip,3L, TimeUnit.SECONDS);// 3秒
        }
        return true;
    }

}
