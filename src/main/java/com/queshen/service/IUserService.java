package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.po.User;
import com.queshen.pojo.bo.WeChatLoginPhoneResponse;
import com.queshen.pojo.bo.WeChatLoginResponse;

/**
 * 用户相关服务
 *
 * @author VioletQin
 * @since 2022/11/10
 */
public interface IUserService extends IService<User> {

     /**
      * 解析微信凭证code
      * @param code
      * @return
      */
     WeChatLoginResponse getLoginResponse(String code);

     /**
      * 获取电话号码
      * @param code
      * @return
      */
     WeChatLoginPhoneResponse getPhoneResponse(String code);

}
