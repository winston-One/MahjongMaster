package com.queshen.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.exceptionhandler.LoginException;
import com.queshen.mapper.UserMapper;
import com.queshen.pojo.bo.WxAccessTokenRes;
import com.queshen.pojo.bo.WxLoginPhoneReq;
import com.queshen.pojo.bo.WxLoginPhoneResponse;
import com.queshen.pojo.bo.WxLoginResponse;
import com.queshen.pojo.po.User;
import com.queshen.service.IUserService;
import com.queshen.utils.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 用户相关服务实现
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    RestTemplate restTemplate;

    @Resource
    WeChatUtil weChatUtil;

    @Override
    public WxLoginResponse getLoginResponse(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+weChatUtil.getAppid()+"" +
                "&secret="+weChatUtil.getSecret()+"" +
                "&js_code="+code+"" +
                "&grant_type=authorization_code";
        String response = restTemplate.getForObject(url,String.class);
        WxLoginResponse loginResponse = JSON.parseObject(response, WxLoginResponse.class);
        assert loginResponse != null;
        Integer errcode = loginResponse.getErrcode();
        if (errcode != null && errcode != 0){
            LoginException loginException = new LoginException();
            loginException.setCode(20001);
            if (errcode == -1){
                loginException.setMsg("系统繁忙");
            }else if (errcode == 40029){
                loginException.setMsg("code无效");
            }else if (errcode == 45011){
                loginException.setMsg("频率限制，每个用户每分钟100次");
            }else if (errcode == 40226){
                loginException.setMsg("高风险等级用户，小程序登录拦截");
            }else {
                loginException.setMsg("code有误");
            }
            throw loginException;
        }
        return loginResponse;
    }


    @Override
    public WxLoginPhoneResponse getPhoneResponse(String code) {

        String access_token;
        // 如果可以从缓存中获取微信接口调用凭证，就不需要重复发送请求获取凭证
        // 第一次登录都会将解析的token存入到Redis缓存中，并有过期时间，频繁发起网络请求时很耗cpu资源的。
        access_token = stringRedisTemplate.opsForValue().get("access_token");
        if (access_token == null) {
            // 获取接口凭证 yaml配置类中小程序信息属于私密信息，小伙伴可以自行配置自己注册的小程序进行测试
            String url = "https://api.weixin.qq.com/cgi-bin/token?appid="+weChatUtil.getAppid()+"" +
                    "&secret="+weChatUtil.getSecret()+""+
                    "&grant_type=client_credential";
            String response = restTemplate.getForObject(url,String.class);
            WxAccessTokenRes tokenResponse = JSON.parseObject(response, WxAccessTokenRes.class);
            assert tokenResponse != null;
            Integer errcode = tokenResponse.getErrcode();
            if (errcode != null && errcode != 0){
                LoginException loginException = new LoginException();
                loginException.setCode(20001);
                if (errcode == -1){
                    loginException.setMsg("系统繁忙");
                }else if (errcode == 40001){
                    loginException.setMsg("AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性");
                }else if (errcode == 40002){
                    loginException.setMsg("请确保 grant_type 字段值为 client_credential");
                }else {
                    loginException.setMsg("不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写");
                }
                throw loginException;
            }
            access_token = tokenResponse.getAccess_token();
            stringRedisTemplate.opsForValue().set("access_token",access_token,7200, TimeUnit.SECONDS);
        }

        // 通过接口凭证获取用户电话号码
        String url1 = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=ACCESS_TOKEN";
        WxLoginPhoneReq request = new WxLoginPhoneReq(access_token, code);
        // 使用restTemplate发起网路请求第三方接口
        String phoneRequest = restTemplate.postForObject(url1,request,String.class);
        // 将获取的结果集进行JSON解析映射到自定义WeChatLoginPhoneResponse实体类中
        WxLoginPhoneResponse phoneResponse = JSON.parseObject(phoneRequest, WxLoginPhoneResponse.class);

        assert phoneResponse != null;
        Integer pErrcode = phoneResponse.getErrcode();
        if (pErrcode != null && pErrcode != 0){
            LoginException loginException = new LoginException();
            loginException.setCode(20001);
            if (pErrcode == -1){
                loginException.setMsg("系统繁忙，此时请开发者稍候再试");
            }else if (pErrcode == 40029){
                loginException.setMsg("不合法的code（code不存在、已过期或者使用过）");
            }else {
                loginException.setMsg("code不合法");
            }
            throw loginException;
        }
        return phoneResponse;
    }
}
