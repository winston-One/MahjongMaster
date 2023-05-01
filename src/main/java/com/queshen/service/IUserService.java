package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.po.User;
import com.queshen.pojo.bo.WxLoginPhoneResponse;
import com.queshen.pojo.bo.WxLoginResponse;

/**
 * 用户相关服务
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public interface IUserService extends IService<User> {

     // 解析微信凭证code
     WxLoginResponse getLoginResponse(String code);

     // 获取电话号码
     WxLoginPhoneResponse getPhoneResponse(String code);

}
