package com.queshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.queshen.mapper.*;
import com.queshen.pojo.admin.*;
import com.queshen.pojo.po.*;
import com.queshen.service.*;
import com.queshen.utils.JwtUtils;
import com.queshen.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author WinstonYv
 * @create 2024/4/24 19:06
 * @Description: Man can conquer nature
 **/
@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    IUserService userService;

    @Resource
    RoomMapper roomMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    StoreService storeService;

    @Resource
    StoreMapper storeMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderService orderService;

    @Resource
    IRoomService roomService;

    @Resource
    VoucherMapper voucherMapper;

    @Resource
    IVoucherService voucherService;

    @PostMapping("/getUserInfo")
    public R getUserInfo(@RequestBody User dto){
        User userData = userMapper.selectById(dto.getOpenid());
        if (StringUtils.isEmpty(userData)) {
            return R.error().message("用户不存在");
        }
        return R.ok().data("data", userData);
    }

    @PostMapping("/getStoreInfo")
    public R getStoreInfo(@RequestBody Store dto){
        Store store = storeMapper.selectById(dto.getStoreId());
        if (StringUtils.isEmpty(store)) {
            return R.error().message("门店不存在");
        }
        return R.ok().data("data", store);
    }

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        log.info("登录: {}", user);

        if (StringUtils.isEmpty(user.getOpenid()) || StringUtils.isEmpty(user.getPassword())) {
            return R.error();
        }
        String userNo = user.getOpenid();
        String password = user.getPassword();
        log.info("密码: {}", password);

        User userData = userMapper.selectById(userNo);
        if (StringUtils.isEmpty(userData)) {
            return R.error().message("用户不存在");
        }
        if (!userData.getPassword().equals(password)) {
            return R.error().message("账号密码错误");
        }
        // 生成令牌
        String token = JwtUtils.generate(userNo);
        // 维护cookie
        return R.ok()
                .data("user", userData)
                .data("token", token)
                .message("登录成功");
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public R logout(@RequestBody User user) {
        log.info("退出登录: {}", user);
        return R.ok().message("退出登录成功");
    }

    @PostMapping("/updatePassword")
    public R updatePassword(@RequestBody UserPasswordDto user){
        log.info("修改密码{}",user);
        String userNo = user.getUserNo();
        String password = user.getPassword();
        String newPassword = user.getNewPassword();

        User user1 = userMapper.selectById(userNo);
        if (StringUtils.isEmpty(user1)) {
            return R.error().message("用户账号不存在");
        }
        if (!user1.getPassword().equals(password)) {
            return R.error().message("原密码错误");
        }
        if (newPassword.equals(password)) {
            return R.error().message("新密码不能与原密码相同");
        }

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("openid", userNo);
        userUpdateWrapper.eq("is_delete", 0);
        userUpdateWrapper.set("password", newPassword);
        int update = userMapper.update(null, userUpdateWrapper);
        if (update > 0) {
            return R.ok().message("密码修改成功");
        }
        return R.error().message("密码修改失败");
    }

    /**
     * 获取所有用户
     */
    @PostMapping("/getAllUser")
    public R getAllUser(@RequestBody User dto) {
        log.info("获取所有用户{}", dto);
        List<User> list = userMapper.selectList(null);
        return R.ok().data("data", list).message("获取数据成功");
    }

    /**
     * 修改用户
     */
    @PostMapping("/updateUser")
    public R updateUser(@RequestBody User dto) {
        log.info("修改用户{}", dto);
        QueryWrapper<User> userUpdateWrapper = new QueryWrapper<>();
        userUpdateWrapper.eq("openid", dto.getOpenid());
        userUpdateWrapper.eq("is_delete", 0);
        User user = userMapper.selectOne(userUpdateWrapper);
        if (user == null) {
            user = new User(
                  UUID.randomUUID().toString()
                , dto.getNickname()
                , "123456"
                , dto.getPhone()
                , 999.99
                , LocalDateTime.now()
                , LocalDateTime.now()
            );
        } else {
            user.setNickname(dto.getNickname());
            user.setPhone(dto.getPhone());
            user.setMoney(dto.getMoney());
        }
        boolean b = userService.saveOrUpdate(user);
        if (!b) {
            return R.error().message("更新用户数据失败");
        }
        return R.ok().message("更新用户数据成功");
    }

    /**
     * 删除用户
     */
    @PostMapping("/deleteUser")
    public R deleteUser(@RequestBody User dto) {
        log.info("删除用户{}", dto);
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("openid", dto.getOpenid());
        wrapper.set("is_delete", 1);
        int update = userMapper.update(null, wrapper);
        if (update <= 0) {
            return R.error().message("删除用户数据失败");
        }
        return R.ok().message("删除用户数据成功");
    }

    /**
     * 获取所有门店
     */
    @PostMapping("/getAllStore")
    public R getAllStore(@RequestBody User dto) {
        log.info("获取所有门店{}", dto);
        List<StoreVo> list = storeMapper.getAllStore();
        for (StoreVo storeVo : list) {
            QueryWrapper<Room> roomQueryWrapper = new QueryWrapper<>();
            roomQueryWrapper.eq("store_id", storeVo.getStoreId());
            roomQueryWrapper.eq("is_deleted", 0);
            List<Room> rooms = roomMapper.selectList(roomQueryWrapper);
            storeVo.setRoomList(rooms);
        }
        return R.ok().data("data", list).message("获取数据成功");
    }

    /**
     * 修改门店
     */
    @PostMapping("/updateStore")
    public R updateStore(@RequestBody StoreVo dto) {
        log.info("修改门店{}", dto);
        if (dto.getStoreId() == null || Objects.equals(dto.getStoreId(), "")) {
            return R.error().message("门店id不能为空");
        }
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.eq("store_id", dto.getStoreId());
        wrapper.eq("is_deleted", 0);
        Store store = storeMapper.selectOne(wrapper);
        if (store == null) {
            store = new Store(
                    dto.getStatus(),
                    dto.getAddress(),
                    dto.getPhone(),
                    dto.getStoreName(),
                    dto.getWifi(),
                    dto.getWifiPassword()
            );
            if (dto.getStoreId() != null && !Objects.equals(dto.getStoreId(), "")) {
                store.setStoreId(dto.getStoreId());
            } else {
                Random rand = new Random();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    int digit = rand.nextInt(10); // 生成0到9之间的随机整数
                    sb.append(digit);
                }
                String storeId = sb.toString();
                store.setStoreId(storeId);
            }
        } else {
            store.setStoreName(dto.getStoreName());
            store.setStatus(dto.getStatus());
            store.setAddress(dto.getAddress());
            store.setWifi(dto.getWifi());
            store.setWifiPassword(dto.getWifiPassword());
            store.setPhone(dto.getPhone());
        }
        boolean b = storeService.saveOrUpdate(store);
        if (!b) {
            return R.error().message("修改门店数据失败");
        }
        return R.ok().message("修改门店数据成功");
    }

    /**
     * 删除门店
     */
    @PostMapping("/deleteStore")
    public R deleteStore(@RequestBody StoreVo dto) {
        log.info("删除门店{}", dto);
        UpdateWrapper<Store> wrapper = new UpdateWrapper<>();
        wrapper.eq("store_id", dto.getStoreId());
        wrapper.set("is_deleted", 1);
        int update = storeMapper.update(null, wrapper);
        if (update <= 0) {
            return R.error().message("删除门店数据失败");
        }
        return R.ok().message("删除门店数据成功");
    }

    /**
     * 获取所有房间
     */
    @PostMapping("/getAllRoom")
    public R getAllRoom(@RequestBody RoomVo dto) {
        log.info("获取所有的房间{}", dto);
        List<RoomVo> list = roomMapper.getData(dto.getStoreId());
        for (RoomVo roomVo : list) {
            Store store = storeMapper.selectById(roomVo.getStoreId());
            if (store != null) {
                roomVo.setStoreName(store.getStoreName());
            }
        }
        return R.ok().data("data", list).message("获取数据成功");
    }

    /**
     * 修改房间
     */
    @PostMapping("/updateRoom")
    public R updateRoom(@RequestBody RoomVo dto) {
        log.info("修改房间{}", dto);
        if (dto.getStoreId() == null || Objects.equals(dto.getStoreId(), "")) {
            return R.error().message("门店id不能为空");
        }
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id", dto.getRoomId());
        wrapper.eq("is_deleted", 0);
        Room room = roomMapper.selectOne(wrapper);
        if (room == null) {
            room = new Room(
                dto.getRoomName(),
                dto.getStoreId(),
                dto.getStatus(),
                dto.getPrice(),
                dto.getRemarks()
            );
            if (dto.getRoomId() != null && !Objects.equals(dto.getRoomId(), "")) {
                room.setRoomId(dto.getRoomId());
            } else {
                Random rand = new Random();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 6; i++) {
                    int digit = rand.nextInt(10); // 生成0到9之间的随机整数
                    sb.append(digit);
                }
                String roomId = sb.toString();
                room.setRoomId(roomId);
            }
            room.setImage("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
        } else {
            room.setRoomName(dto.getRoomName());
            room.setRemarks(dto.getRemarks());
            room.setPrice(dto.getPrice());
            room.setStoreId(dto.getStoreId());
            room.setStatus(dto.getStatus());
        }
        boolean b = roomService.saveOrUpdate(room);
        if (!b) {
            return R.error().message("修改房间数据失败");
        }
        return R.ok().message("修改房间数据成功");
    }

    /**
     * 删除房间
     */
    @PostMapping("/deleteRoom")
    public R deleteRoom(@RequestBody RoomVo dto) {
        log.info("删除房间{}", dto);
        UpdateWrapper<Room> wrapper = new UpdateWrapper<>();
        wrapper.eq("room_id", dto.getRoomId());
        wrapper.set("is_deleted", 1);
        int update = roomMapper.update(null, wrapper);
        if (update <= 0) {
            return R.error().message("删除房间数据失败");
        }
        return R.ok().message("删除房间数据成功");
    }

    /**
     * 获取所有订单
     */
    @PostMapping("/getAllOrder")
    public R getAllOrder(@RequestBody User dto) {
        log.info("获取所有订单{}", dto);
        List<OrderInfoVo> list = orderMapper.getData();
        for (OrderInfoVo orderInfoVo : list) {
            Room room = roomMapper.selectById(orderInfoVo.getRoomId());
            if (room==null) {
                orderInfoVo.setRoomName("");
            } else {
                orderInfoVo.setRoomName(room.getRoomName());
            }
            Store store = storeMapper.selectById(orderInfoVo.getStoreId());
            if (store==null) {
                orderInfoVo.setStoreName("");
            } else {
                orderInfoVo.setStoreName(store.getStoreName());
            }
            User user = userMapper.selectById(orderInfoVo.getUserId());
            if (user==null) {
                orderInfoVo.setNickname("匿名");
            } else {
                orderInfoVo.setNickname(user.getNickname());
            }
            if (orderInfoVo.getPayTime() == null) {
                orderInfoVo.setPayTime(LocalDateTime.now());
            }
        }
        return R.ok().data("data", list);
    }

    /**
     * 修改订单
     */
    @PostMapping("/updateOrder")
    public R updateOrder(@RequestBody OrderInfoVo dto) {
        log.info("修改订单{}", dto);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", dto.getId());
        wrapper.eq("is_delete", 0);
        Order order = orderMapper.selectOne(wrapper);
        if (order == null) {
            return R.error().message("订单为空！！");
        } else {
            order.setStatus(dto.getStatus());
            order.setRoomId(dto.getRoomId());
            order.setStoreId(dto.getStoreId());
            order.setStartTime(dto.getStartTime());
            order.setEndTime(dto.getEndTime());
        }
        boolean b = orderService.saveOrUpdate(order);
        if (!b) {
            return R.error().message("修改订单数据失败");
        }
        return R.ok().message("获取订单数据成功");
    }

    /**
     * 删除订单
     */
    @PostMapping("/deleteOrder")
    public R deleteOrder(@RequestBody OrderInfoVo dto) {
        log.info("删除订单{}", dto);
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", dto.getId());
        wrapper.set("is_delete", 1);
        int update = orderMapper.update(null, wrapper);
        if (update <= 0) {
            return R.error().message("删除订单数据失败");
        }
        return R.ok().message("删除订单数据成功");
    }

    /**
     * 获取所有卡券
     */
    @PostMapping("/getAllVoucher")
    public R getAllVoucher(@RequestBody User dto) {
        log.info("获取所有卡券{}", dto);
        QueryWrapper<Voucher> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        List<Voucher> list = voucherMapper.selectList(wrapper);
        return R.ok().data("data", list).message("获取数据成功");
    }

    /**
     * 修改卡券
     */
    @PostMapping("/updateVoucher")
    public R updateVoucher(@RequestBody CouponVo dto) {
        log.info("修改卡券{}", dto);
        QueryWrapper<Voucher> wrapper = new QueryWrapper<>();
        wrapper.eq("voucher_id", dto.getVoucherId());
        wrapper.eq("is_delete", 0);
        Voucher voucher = voucherMapper.selectOne(wrapper);
        if (voucher == null) {
            voucher = new Voucher(
                dto.getTitle(),
                dto.getAvailableRange(),
                dto.getPrice(),
                dto.getTerm(),
                dto.getVouStatus(),
                LocalDateTime.now(),
                LocalDateTime.now()
            );
        } else {
            voucher.setTitle(dto.getTitle());
            voucher.setPrice(dto.getPrice());
            voucher.setAvailableRange(dto.getAvailableRange());
            voucher.setTerm(dto.getTerm());
            voucher.setVouStatus(dto.getVouStatus());
            voucher.setUpdateTime(LocalDateTime.now());
        }
        boolean b = voucherService.saveOrUpdate(voucher);
        if (!b) {
            return R.error().message("修改卡券数据失败");
        }
        return R.ok().message("修改卡券数据成功");
    }

    /**
     * 删除卡券
     */
    @PostMapping("/deleteVoucher")
    public R deleteVoucher(@RequestBody CouponVo dto) {
        log.info("删除卡券{}", dto);
        UpdateWrapper<Voucher> wrapper = new UpdateWrapper<>();
        wrapper.eq("voucher_id", dto.getVoucherId());
        wrapper.set("is_delete", 1);
        int update = voucherMapper.update(null, wrapper);
        if (update <= 0) {
            return R.error().message("删除卡券数据失败");
        }
        return R.ok().message("删除卡券数据成功");
    }
}
