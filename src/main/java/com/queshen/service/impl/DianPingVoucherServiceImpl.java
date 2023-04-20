package com.queshen.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.DianPingVoucherOrder;
import com.queshen.mapper.DianPingVoucherOrderMapper;
import com.queshen.service.DianPingVoucherService;
import com.queshen.pojo.vo.DianPingCanDoVoucher;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Log4j2
@Component
public class DianPingVoucherServiceImpl extends ServiceImpl<DianPingVoucherOrderMapper, DianPingVoucherOrder> implements DianPingVoucherService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 将标题处理成内容
     * @param orderId
     * @param tittle
     * @return
     */
    public DianPingVoucherOrder doDianPingTittle(String orderId,String tittle,String userId) {
        if (tittle.isEmpty()||!tittle.contains("【")||!tittle.contains("】")||!tittle.contains("/")||!tittle.contains("小时"))
            return null;

        DianPingVoucherOrder dianPingVoucherOrder=new DianPingVoucherOrder();
        List<String> room=new ArrayList<>();
        //处理价格和时长
        String s = StringUtils.substringBetween(tittle, "【", "】");
        //处理价格
        String s1 = StringUtils.substringBefore(s, "元");
        //处理时长
        String s2 = StringUtils.substringBetween(s, "/", "小");
        //处理适用范围
        String s3 = StringUtils.substringBetween(tittle, "】", "房型");
        //处理备注
        String s5 = StringUtils.substringAfterLast(tittle, " ");
        String s8=  "dianping"+orderId;
        if (s1.isEmpty()||s2.isEmpty()||s3.isEmpty())
            return null;
        if (s3.contains("/")){
            for (String s4 : s3.split("/")) {
                room.add(s4);
            }
        }else{
            room.add(s3);
        }
        //NumberFormatException
        try {
            BigDecimal price=new BigDecimal(s1);
            BigDecimal duration=new BigDecimal(s2);
            dianPingVoucherOrder.setDuration(duration);
            dianPingVoucherOrder.setPrice(price);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return null;
        }


        dianPingVoucherOrder.setId(s8);

        dianPingVoucherOrder.setUserId(userId);
        dianPingVoucherOrder.setRoom(room);
        dianPingVoucherOrder.setPS(s5);

        return dianPingVoucherOrder;
    }

    /**
     * 将预约完的订单放进数据库中
     * @param dianPingVoucherOrder
     * @return
     */
    public Boolean doDPOrderToMysql(DianPingVoucherOrder dianPingVoucherOrder){
        String s = stringRedisTemplate.opsForValue().get(dianPingVoucherOrder.getUserId() + dianPingVoucherOrder.getId());
        DianPingVoucherOrder dianPingVoucherOrder1 = JSONUtil.toBean(s, DianPingVoucherOrder.class);
        dianPingVoucherOrder1.setStatus(1);
        stringRedisTemplate.delete(dianPingVoucherOrder1.getUserId()+dianPingVoucherOrder1.getId());
        this.save(dianPingVoucherOrder1);
        return true;
    }

    /**
     * 将销完券的订单放进reids中
     * @param dianPingVoucherOrder
     * @return
     */
    public Boolean doDPOrderToRedis(DianPingVoucherOrder dianPingVoucherOrder){
        dianPingVoucherOrder.setStatus(0);
        stringRedisTemplate.opsForValue().set(dianPingVoucherOrder.getUserId()+dianPingVoucherOrder.getId(), JSONUtil.toJsonStr(dianPingVoucherOrder));
        return true;
    }

    /**
     * 查询可用的券（在redis中的）
     * @param userid
     * @param pageNum
     * @return
     */
    public Result selectDPOrderInRedis(String userid,int pageNum){

        String key = userid + "*";
        Set<String> keysList = stringRedisTemplate.keys(key);
        List<DianPingVoucherOrder> voucherOrderList = new ArrayList<>();
        if (keysList.size() == 0)
            return Result.ok(null);

        List<String> strings = stringRedisTemplate.opsForValue().multiGet(keysList);

        DianPingVoucherOrder voucherOrder;
        for (String s : strings) {
            voucherOrder=JSONUtil.toBean(s,DianPingVoucherOrder.class);
            voucherOrderList.add(voucherOrder);
        }
        IPage<DianPingVoucherOrder> iPage =new Page(pageNum,voucherOrderList.size());
        iPage.setRecords(voucherOrderList);
        return Result.ok(iPage);
    }

    /**
     * 查询可用券 （新）
     * @param userid
     * @return
     */
    public Result selectCandoDPVoucherInRedis(String userid){

        String key = userid + "*";
        Set<String> keysList = stringRedisTemplate.keys(key);
        log.info(keysList+userid);
        List<DianPingCanDoVoucher> dianPingCanDoVoucherList = new ArrayList<>();
        if (keysList.size() == 0)
            return Result.ok(null);

        List<String> strings = stringRedisTemplate.opsForValue().multiGet(keysList);
        DianPingVoucherOrder voucherOrder;
        for (String s : strings) {
            DianPingCanDoVoucher dianPingCanDoVoucher=new DianPingCanDoVoucher();
            voucherOrder=JSONUtil.toBean(s,DianPingVoucherOrder.class);
            dianPingCanDoVoucher.setId(voucherOrder.getId());
            dianPingCanDoVoucher.setPrice(voucherOrder.getPrice());
            dianPingCanDoVoucher.setDuration(voucherOrder.getDuration());
            dianPingCanDoVoucherList.add(dianPingCanDoVoucher);
        }
        return Result.ok(dianPingCanDoVoucherList);
    }


    /**
     * 查询所有的大众券
     * @param userid
     * @param pageNum
     * @return IPage<DianPingVoucherOrder>
     */
    public Result selectAllDPOrder(String userid,int pageNum) {
        String key = userid + "*";
        Set<String> keysList = stringRedisTemplate.keys(key);
        List<DianPingVoucherOrder> voucherOrderList = new ArrayList<>();
        if (keysList.size() != 0) {
            List<String> strings = stringRedisTemplate.opsForValue().multiGet(keysList);


            DianPingVoucherOrder voucherOrder= new DianPingVoucherOrder();

            for (String s : strings) {
                voucherOrder=JSONUtil.toBean(s,DianPingVoucherOrder.class);
                voucherOrderList.add(voucherOrder);
            }
        }

        IPage<DianPingVoucherOrder> iPage=new Page<>(pageNum,10);
        LambdaQueryWrapper<DianPingVoucherOrder> lqw=new LambdaQueryWrapper<>();
        lqw.eq(DianPingVoucherOrder::getUserId,userid);
        IPage<DianPingVoucherOrder> page = this.page(iPage, lqw);
        List<DianPingVoucherOrder> list = page.getRecords();
        voucherOrderList.addAll(list);
        iPage.setRecords(voucherOrderList);
        return Result.ok(iPage);
    }

}
