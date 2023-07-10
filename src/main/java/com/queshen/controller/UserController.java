package com.queshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.queshen.cache.RedisCache;
import com.queshen.config.UserOnlineListener;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.UserDTO;
import com.queshen.pojo.dto.WxLoginDTO;
import com.queshen.pojo.dto.WxPhoneDTO;
import com.queshen.pojo.po.User;
import com.queshen.pojo.bo.WxLoginPhoneResponse;
import com.queshen.pojo.bo.WxLoginResponse;
import com.queshen.service.IUserService;
import com.queshen.utils.JwtUtils;
import com.queshen.utils.UserHolder;
import com.queshen.pojo.vo.QuickLoginVO;
import com.queshen.pojo.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    /**
     * 微信登录功能
     * @param loginDTO 登录参数，包含微信凭证code和头像和昵称
     */
    @PostMapping("/login")
    public Result login(@RequestBody WxLoginDTO loginDTO, HttpSession httpSession) {
        // 对前端微信登录获得的code参数进行处理，以至于获得openid和头像和昵称信息
        WxLoginResponse loginResponse = userService.getLoginResponse(loginDTO.getCode());
        if (loginResponse == null) {
            return Result.fail("解析用户凭证失败");
        }
        String openid = loginResponse.getOpenid();
        String unionid = loginResponse.getUnionid();

        // 登录之后保存用户数据到UserHold中
        UserDTO userHold = new UserDTO(openid, loginDTO.getNickname(), loginDTO.getAvatarUrl());
        UserHolder.saveUser(userHold);
        User user = cache.get(openid);
        if (StringUtils.isEmpty(user)) {
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
        // 监听用户在线，方便统计用户在线人数，将属性设置到session中存储起来
        httpSession.setAttribute("userOnlineListener", new UserOnlineListener(userLoginVo.getOpenid()));
        return Result.ok(userLoginVo);
    }

    @PostMapping( "/getUserOnline")
    public Result getUserOnline(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext application = session.getServletContext();
        List<String> userOnlineList= (List<String>) application.getAttribute("userOnlineList");
        if(userOnlineList!=null){
            log.info("在线用户数==={}", userOnlineList.size());
        }
        return Result.ok(userOnlineList);
    }

    @PostMapping("/quickLogin")
    public Result quickLogin(@RequestBody WxLoginDTO weChatLoginDTO) {
        WxLoginResponse loginResponse = userService.getLoginResponse(weChatLoginDTO.getCode());
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
    public Result savePhone(@RequestBody WxPhoneDTO phoneDTO) {
        String phone = userService.getById(phoneDTO.getOpenid()).getPhone();
        if(phone != null){
            return Result.ok(phone);
        }
        WxLoginPhoneResponse phoneResponse = userService.getPhoneResponse(phoneDTO.getCode());
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

    @GetMapping("/current")
    public Result current() {
        //获取当前用户的登录信息并返回
        UserDTO current = UserHolder.getUser();
        return Result.ok(current);
    }

    /**
     * 退出登录还需要清除前端的缓存
     * @return
     */
    @GetMapping("/logout")
    public Result logout(@RequestParam("openid") String openid) {
        cache.remove(openid);
        UserHolder.removeUser();
        return Result.ok("退出登录成功");
    }
}
