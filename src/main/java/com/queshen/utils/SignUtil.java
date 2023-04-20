package com.queshen.utils;

import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
//北极星平台签名工具类
public class SignUtil {

    /**
     * 生成签名
     * @param params     api请求参数
     * @param appSecret  app密码
     * @param signMethod 加密方法MD5
     * @return
     */
    public static String generateSign(Map<String, String> params, String appSecret, String signMethod){
        //第一步：参数排序
        List<String> keys = Lists.newArrayList();
        for (Map.Entry<String,String> entry :params.entrySet()){
            if (!StringUtils.isEmpty(entry.getValue()))
                keys.add(entry.getKey());
        }
        Collections.sort(keys);

        //第二步：把所有参数名和参数值串起来
        //appSecret+排好序的所有参数名和参数值串+appSecret
        StringBuilder sb=new StringBuilder();
        if (!StringUtils.isEmpty(appSecret)){
            sb.append(appSecret);
        }
        for (String key : keys){
            sb.append(key).append(params.get(key).trim());
        }
        if (!StringUtils.isEmpty(appSecret)){
            sb.append(appSecret);
        }
        String encryptionKey =sb.toString().trim();

        //第三步：进行加签使用MD5
        if (signMethod.equals("MD5")){

            String sign = null;
            try {
                sign = genMd5(encryptionKey);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return sign;
        }
        else
            return null;
    }

    //使用签名算法对编码后的字节流进行摘要。使用MD5算法
    private static String genMd5(String info) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] infoBytes = info.getBytes("UTF-8");
        //进行加签
        md5.update(infoBytes);
        byte[] sign = md5.digest();
        //将处理好的签名转成16进制位
        return byteArrayToHex(sign);
    }


    //进制转换
    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toLowerCase());
        }
        return sign.toString();
    }

}
