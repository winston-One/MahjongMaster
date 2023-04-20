package com.queshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.queshen.cache.RedisCache;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.UserDTO;
import com.queshen.pojo.dto.WeChatLoginDTO;
import com.queshen.pojo.dto.WeChatPhoneDTO;
import com.queshen.pojo.po.User;
import com.queshen.pojo.bo.WeChatLoginPhoneResponse;
import com.queshen.pojo.bo.WeChatLoginResponse;
import com.queshen.service.IUserService;
import com.queshen.utils.JwtUtils;
import com.queshen.utils.UserHolder;
import com.queshen.pojo.vo.QuickLoginVO;
import com.queshen.pojo.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/12
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Resource(name = "loginCache")
    RedisCache<User> cache;

    @GetMapping("/login1")
    public Result login(String username,String Password) {
        
        return Result.ok();
    }
    /**
     * 微信登录功能
     * @param loginDTO 登录参数，包含微信凭证code和头像和昵称
     */
    @PostMapping("/login")
    public Result login(@RequestBody WeChatLoginDTO loginDTO) {

        WeChatLoginResponse loginResponse = userService.getLoginResponse(loginDTO.getCode());
        if (loginResponse == null) {
            return Result.fail("解析用户凭证失败");
        }
        String openid = loginResponse.getOpenid();
        String unionid = loginResponse.getUnionid();

        // 登录之后保存用户数据到UserHold中
        UserDTO userHold = new UserDTO(openid, loginDTO.getNickname(), loginDTO.getAvatarUrl());
        UserHolder.saveUser(userHold);
        User user = cache.get(openid);
        if (user == null) {
            user = new User();
            BeanUtils.copyProperties(loginDTO,user);
            user.setOpenid(openid);
            user.setUnionId(unionid);
            // 如果用户之前已经登录过，就不需要重复在数据库表中存储多一份数据
            if (userService.getById(user.getOpenid()) == null) {
                userService.save(user);
            }
            cache.put(openid,user);
        }
        String token = JwtUtils.generate(openid);
        UserLoginVo userLoginVo = new UserLoginVo(token, openid,loginDTO.getAvatarUrl(),loginDTO.getNickname());
        return Result.ok(userLoginVo);
    }

    @PostMapping("/quickLogin")
    public Result quickLogin(@RequestBody WeChatLoginDTO weChatLoginDTO) {
        WeChatLoginResponse loginResponse = userService.getLoginResponse(weChatLoginDTO.getCode());
        String openid = loginResponse.getOpenid();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid", openid);
        User user = userService.getOne(userQueryWrapper);
        if (user == null) {
            return Result.fail("账户未注册");
        }
        User userCache = cache.get(openid);
        if (userCache == null) {
            cache.put(openid,user);
        }
        String token = JwtUtils.generate(openid);
        QuickLoginVO quickLoginVO = new QuickLoginVO(openid,user.getAvatarUrl(),
                user.getNickname(),token);
        return Result.ok(quickLoginVO);
    }

    /**
     * 获取电话号码
     * @param phoneDTO 获取用户电话号码的请求参数
     */
    @PostMapping("/savePhone")
    public Result savePhone(@RequestBody WeChatPhoneDTO phoneDTO) {
        String phone = userService.getById(phoneDTO.getOpenid()).getPhone();
        if(phone != null){
            return Result.ok(phone);
        }
        WeChatLoginPhoneResponse phoneResponse = userService.getPhoneResponse(phoneDTO.getCode());
        if(phoneResponse == null){
            return Result.fail("解析手机号凭证失败");
        }
        phone = phoneResponse.getPhone_info().getPhoneNumber();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("openid", phoneDTO.getOpenid());
        User user = new User();
        user.setPhone(phone);
        userService.update(user,updateWrapper);
        return Result.ok(phone);
    }

    // 更新用户的昵称和微信头像
    @PostMapping("/update")
    public Result updateNickNameAndAvatarUrl(@RequestBody WeChatLoginDTO updateUser){

//        String openid = updateUser.getCode();
//
//        User user = userService.getById(openid);
//        BeanUtils.copyProperties(updateUser,user);
//        userService.updateById(user);
//        cache.remove(openid);

        return Result.ok();
    }

    /**
     * 获取个人openId
     */
    @GetMapping("/getOpenId")
    public Result getOpenId(){
        return Result.ok(UserHolder.getUser().getOpenid());
    }

    @GetMapping("/current")
    public Result current() {
        //获取当前用户的登录信息并返回
        UserDTO current = UserHolder.getUser();
        return Result.ok(current);
    }

    /**
     * logout
     * @return
     */
    @GetMapping("/logout")
    public Result logout(@RequestParam("openid") String openid) {

        cache.remove(openid);
        UserHolder.removeUser();
        return Result.ok("退出登录成功");

    }

    @GetMapping("/test")
    public Result test() {
        return Result.ok("test Success");
    }

}
