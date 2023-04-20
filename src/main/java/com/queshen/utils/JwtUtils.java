package com.queshen.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

/**
 * @author WinstonYv
 * @create 2022/11/14
 * @Description:
 **/
@Slf4j
public final class JwtUtils {
    /**
     * 生成一个秘钥,防止JWT被篡改的关键，
     */
    private final static String secretKey = "winston9790834616898adlmasdfiosalfmsdpofasdfmasdfasmmapedf54641asfd8fas654f4a6sf";
    /**
     * 过期时间目前设置成10天
     */
    private final static Duration expiration = Duration.ofDays(10);

    /**
     * 生成JWT
     * @param userName 用户名
     * @return JWT
     */
    public static String generate(String userName) {
        // 过期时间
        Date expiryDate = new Date(System.currentTimeMillis() + expiration.toMillis());
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .setSubject(userName) // 将openId放进JWT
                .setIssuedAt(new Date()) // 设置JWT签发时间
                .setExpiration(expiryDate)  // 设置过期时间
                .signWith(key) // 设置加密算法和秘钥
                .compact();
    }

    /**
     * 解析JWT
     * @param token JWT字符串
     * @return 解析成功返回Claims对象，只需要关心是否能解析，不需要理会解析的信息是什么
     */
    public static Claims parse(String token) {
        // 如果是空字符串直接返回null
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        // Claims对象包含了生成jwt的信息
        Claims claims = null;
        // 解析失败了会抛出异常,例如：token过期、token非法等等
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 设置秘钥
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.err.println("解析失败！");
            log.info(e.getMessage());
        }
        return claims;
    }
}
