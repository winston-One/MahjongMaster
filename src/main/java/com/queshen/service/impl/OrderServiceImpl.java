package com.queshen.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.queshen.mapper.VoucherOrderMapper;
import com.queshen.pojo.dto.OrderDTO;
import com.queshen.pojo.dto.OrderTime;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.VoucherOrderDTO;
import com.queshen.pojo.po.Order;
import com.queshen.pojo.po.Room;
import com.queshen.pojo.po.Store;
import com.queshen.mapper.OrderMapper;
import com.queshen.pojo.po.VoucherOrder;
import com.queshen.service.IRoomService;
import com.queshen.service.OrderService;
import com.queshen.service.StoreService;
import com.queshen.utils.IdUtil;
import com.queshen.pojo.vo.OrderSaveVo;
import com.queshen.pojo.vo.OrderSelectByUserVO;
import com.queshen.pojo.vo.OrderSelectReturnVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Log4j2
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private IdUtil idUtil;

    @Resource
    private IRoomService iRoomService;

    @Resource
    private StoreService storeService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    VoucherOrderMapper voucherOrderMapper;

    @Resource(name = "voucherListCache")
    LoadingCache<String, List<VoucherOrderDTO>> voucherListCache;

    //查询该用户所有订单信息
    public Result getAllOrderByUser(OrderSelectByUserVO orderSelectByUserVO){
        //查询进行中的订单信息
//        if (orderSelectByUserVO.getOrderStatus() != 0){
//            if (orderSelectByUserVO.getOrderStatus() == 2) {
//                Result orderInRedis = getOrderInRedisBySelect(orderSelectByUserVO);
//                List<Order> data = (ArrayList) orderInRedis.getData();
//                IPage<Order> page = new Page<>(orderSelectByUserVO.getPageNum(), 10);
//                page.setRecords(data);
//                return doSelectData(page);
//            }
//        }
//        List<Order> data = null;
//        if (orderSelectByUserVO.getOrderStatus() == 0){
//            Result orderInRedis = getOrderInRedisBySelect(orderSelectByUserVO);
//            data = (ArrayList<Order>) orderInRedis.getData();
//        }

        //查询已完成或者已取消的订单信息
        //分页查询1页十条数据
        IPage<Order> page = new Page<>(orderSelectByUserVO.getPageNum(), 10);
        LambdaQueryWrapper<Order> lqw = new LambdaQueryWrapper<>();

        lqw.eq(Order::getUserId, orderSelectByUserVO.getOpenId());
        //是否查询该店的订单，否的话就查询所有店的订单
        if (orderSelectByUserVO.getStoreId() != null && !Objects.equals(orderSelectByUserVO.getStoreId(), ""))
            lqw.eq(Order::getStoreId, orderSelectByUserVO.getStoreId());
        //是否查询有状态的订单，否的就查询所有类型的订单
        if (orderSelectByUserVO.getOrderStatus() != 0)
            lqw.eq(Order::getStatus, orderSelectByUserVO.getOrderStatus());
        //按照时间顺序倒序
//        lqw.orderByDesc(Order::getStartTime);

        IPage<Order> orderPage = this.page(page, lqw);
        List<Order> records = orderPage.getRecords();
        ArrayList<Order> arrayList = new ArrayList<>(records);
        orderPage.setRecords(arrayList);
        orderPage.setTotal(arrayList.size());
        return doSelectData(orderPage);
    }

    //处理查询的数据返回
    private Result doSelectData(IPage<Order> page){
        IPage<OrderSelectReturnVO> orderSelectReturnVOIPage = new Page<>(page.getCurrent(),10);
        List<Order> records = page.getRecords();
        List<OrderSelectReturnVO> orderSelectReturnVOS=new ArrayList<>();
        List<Store> storeList = storeService.list();
        List<Room> roomList = iRoomService.list();
        for (Order order:records){
            OrderSelectReturnVO orderSelectReturnVO=new OrderSelectReturnVO();
            for(Store store:storeList){
                if (order.getStoreId().equals(store.getStoreId())){
                    orderSelectReturnVO.setStoreName(store.getStoreName());
                    break;
                }
            }
            for(Room room:roomList){
                if (order.getRoomId().equals(room.getRoomId())){
                    orderSelectReturnVO.setRoomName(room.getRoomName());
                    break;
                }
            }
            Duration duration=Duration.between(order.getStartTime(),order.getEndTime());

            long l1 = duration.toMinutes() % 60;
            long l2 = duration.toHours();
            String durationString= l2 + "小时" + l1 + "分钟";

            orderSelectReturnVO.setOrderStatus(order.getStatus());
            orderSelectReturnVO.setDuration(durationString);
            orderSelectReturnVO.setImgUrl(order.getImage());
            orderSelectReturnVO.setIsVoucher(order.getIsVoucher());
            orderSelectReturnVO.setVoucherId(order.getVoucherId());
            orderSelectReturnVO.setPrice(order.getPrice());
            orderSelectReturnVO.setStartTime(order.getStartTime());
            orderSelectReturnVO.setEndTime(order.getEndTime());
            orderSelectReturnVO.setOrderId(order.getId());
            if (order.getPayTime() != null)
                orderSelectReturnVO.setPayTime(order.getPayTime());
            else
                orderSelectReturnVO.setPayTime(null);
            orderSelectReturnVO.setCreateTime(order.getCreateTime());
            log.info("78789{}", orderSelectReturnVO.getCreateTime());
            orderSelectReturnVOS.add(orderSelectReturnVO);
        }

        orderSelectReturnVOIPage.setRecords(orderSelectReturnVOS);
        orderSelectReturnVOIPage.setTotal(orderSelectReturnVOS.size());
        return Result.ok(orderSelectReturnVOIPage);
    }

    /**
     * 支付成功后调用，处理redis中的数据到Mysql中
     */
    public Result userPointOrder(OrderSaveVo orderSaveVo){
        //在redis中查询到支付完成的订单
        String key= orderSaveVo.getUserId()+"||"+orderSaveVo.getId();
        String value = stringRedisTemplate.opsForValue().get(key);
        Order order = JSONUtil.toBean(value, Order.class);
        String keyOrderTime=order.getStoreId()+order.getRoomId()+"+"+ order.getStartTime().toEpochSecond(ZoneOffset.of("+8"));

        //将订单状态改变成已支付
        order.setStatus(1);
        //增加支付时间
        order.setPayTime(LocalDateTime.now());
        //删除redis中的已支付订单
        stringRedisTemplate.delete(key);
        //修改时间
        String s = stringRedisTemplate.opsForValue().get(keyOrderTime);
        OrderTime orderTime = JSONUtil.toBean(s, OrderTime.class);
        long l1 = orderTime.getEndTime().toEpochSecond(ZoneOffset.of("+8"));
        long l2 = System.currentTimeMillis() / 1000;
        stringRedisTemplate.opsForValue().set(keyOrderTime,JSONUtil.toJsonStr(orderTime),l1-l2,TimeUnit.SECONDS);
        this.updateById(order);
        return Result.ok();
    }

    public static void main(String[] args) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        System.out.println(orderService.getOrderInRedisBySelect(new OrderSelectByUserVO()));
    }

    /**
     * 处理redis中的订单(查询中使用)
     */
    private Result getOrderInRedisBySelect(OrderSelectByUserVO orderSelectByUserVO){
        String keys = orderSelectByUserVO.getOpenId() + "||" + "*";
        Set<String> keysList = stringRedisTemplate.keys(keys);
        assert keysList != null;
        List<String> strings = stringRedisTemplate.opsForValue().multiGet(keysList);
        List<Order> expireOrderList = new ArrayList<>();
        List<Order> unExpireOrderList = new ArrayList<>();
        assert strings != null;
        for (String s : strings) {
            Order order = new Order();
            log.info(s);
            OrderDTO orderDTO = JSONUtil.toBean(s, OrderDTO.class);
            BeanUtils.copyProperties(orderDTO, order, "expireTime");
            //处理过期订单
            if (orderDTO.getExpireTime().getTime() < System.currentTimeMillis()) {
                order.setStatus(3);
                expireOrderList.add(order);
                stringRedisTemplate.delete(orderDTO.getUserId() + "||" + orderDTO.getId());
            } else {
                unExpireOrderList.add(order);
            }
        }
        if (!expireOrderList.isEmpty())
            doOrderToMysql(expireOrderList);
        return Result.ok(unExpireOrderList);
    }

    /**
     * 下单
     * synchronized就是避免几个用户同时对同一个预约时间进行下单，因为一开始点进去查看的预约时间是一样的
     */
    @Override
    public Result saveOneUserOrder(OrderSaveVo orderSaveVO) {
        LocalDateTime startTime = LocalDateTime.ofInstant(orderSaveVO.getStartTime().toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(orderSaveVO.getEndTime().toInstant(), ZoneId.systemDefault());
        //判断当前下单的预约时间是否已经不能预约了
//        Integer isReserve = orderMapper.isExistReserveTime(startTime,endTime).size();
//        if (isReserve > 0) {
//            return Result.fail("该预约时间错误");
//        }
        //获取订单图片
        String byRoomId = iRoomService.getById(orderSaveVO.getRoomId()).getImage();
        orderSaveVO.setId(idUtil.getOrderId(orderSaveVO.getUserId()));
        //将orderSaveVo复制给orderDTO
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderSaveVO, orderDTO, "startTime", "endTime");
        orderDTO.setVoucherId(orderSaveVO.getVoucherId());
        orderDTO.setIsVoucher(orderSaveVO.getIsVoucher());
        log.info("卡券{}", orderSaveVO.getVoucherId());
        //订单过期时间为15分钟
        long l = System.currentTimeMillis() + (15 * 60 * 1000);
        orderDTO.setExpireTime(new Date(l));
        orderDTO.setStartTime(startTime);
        orderDTO.setEndTime(endTime);
        orderDTO.setImage(byRoomId);
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order, "expireTime");
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        UpdateWrapper<VoucherOrder> wrapper = new UpdateWrapper<>();
        wrapper.eq("voucher_id", orderSaveVO.getVoucherId());
        wrapper.eq("user_id", orderSaveVO.getUserId());
        wrapper.set("order_status", 3);
        voucherOrderMapper.update(null, wrapper);
        voucherListCache.invalidate(orderSaveVO.getUserId());

        this.save(order);
        //创建缓存用户订单过期时间信息的key
        String key = orderDTO.getUserId() + "||" + orderDTO.getId();
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(orderDTO));// 存储到Redis中
        saveTimeToRedis(order);// 保存到Redis
        return Result.ok(order.getId());
    }

    /**
     * 将redis中的订单数据加入Mysql数据库
     */
    private void doOrderToMysql(List<Order> orderList){
        this.updateBatchById(orderList);
    }

    /**
     * 删除订单
     */
    public Result deleteOrder(OrderSaveVo orderSaveVo){
        String key=orderSaveVo.getUserId()+"||"+orderSaveVo.getId();
        String s = stringRedisTemplate.opsForValue().get(key);
        Order order = JSONUtil.toBean(s, Order.class);
        Boolean delete = stringRedisTemplate.delete(key);
        if (Boolean.TRUE.equals(delete)) {
            String keyOrderTime=order.getStoreId()+order.getRoomId()+"+"+ order.getStartTime().toEpochSecond(ZoneOffset.of("+8"));
            order.setStatus(3);
            this.updateById(order);
            stringRedisTemplate.delete(keyOrderTime);
            return Result.ok("删除成功");
        }
        else
            return Result.fail("删除失败");
    }

    /**
     * 下单时时间数据存redis里
     */
    private void saveTimeToRedis(Order order){
        OrderTime orderTime=new OrderTime();
        orderTime.setStoreId(order.getStoreId());
        orderTime.setRoomId(order.getRoomId());
        orderTime.setEndTime(order.getEndTime());
        orderTime.setStartTme(order.getStartTime());
        //命名规则  门店号+房间号+|+|+开始时间
        String key = order.getStoreId()+order.getRoomId() + "+" + order.getStartTime().toEpochSecond(ZoneOffset.of("+8"));
        long timeout = 60*15;
        String value = JSONUtil.toJsonStr(orderTime);
        log.info(value);
        log.info(key);
        stringRedisTemplate.opsForValue().set(key,value,timeout,TimeUnit.SECONDS);
    }

    /**
     * 查询时间是否存在
     */
    public Boolean judgeTimeExist(OrderSaveVo orderSaveVo){
        String key = orderSaveVo.getStoreId()+orderSaveVo.getRoomId()+"+"+"*";
        Set<String> keysList = stringRedisTemplate.keys(key);
        LocalDateTime startTime = LocalDateTime.ofInstant(orderSaveVo.getStartTime().toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(orderSaveVo.getEndTime().toInstant(), ZoneId.systemDefault());
        long l1 = startTime.toEpochSecond(ZoneOffset.of("+8"));
        long l2 = endTime.toEpochSecond(ZoneOffset.of("+8"));
        assert keysList != null;
        if (!keysList.isEmpty()){
            List<String> strings = stringRedisTemplate.opsForValue().multiGet(keysList);

            assert strings != null;
            for (String s: strings){
                OrderTime orderTime = JSONUtil.toBean(s, OrderTime.class);
                log.info(orderTime);
                long l3 = orderTime.getStartTme().toEpochSecond(ZoneOffset.of("+8"));
                long l4 = orderTime.getEndTime().toEpochSecond(ZoneOffset.of("+8"));
                if (l1 >= l3 && l1 <= l4)
                    return false;
                if (l2 >= l3 && l2 <= l4)
                    return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean checkPaySuccess(WxPayOrderNotifyResult result) {
        String success = "SUCCESS";
        if (success.equals(result.getReturnCode())) {
            if (success.equals(result.getResultCode())) {
                return true;
            }
        }
        log.info("微信支付checkPaySuccess失败 {}", result.getReturnCode());
        return false;
    }

    public Boolean selectIsDoingOrder(){
        //1.根据用户id是否存在redis中
//        String userId= UserHolder.getUser().getOpenid();
        String userId= "o2eui5ZuZQt2eEsO7lyq0psWFXYg";
        String key=userId+"||*";
        Set<String> keysList = stringRedisTemplate.keys(key);
        assert keysList != null;
        return keysList.isEmpty();
    }
}
