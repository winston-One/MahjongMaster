package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.DianPingVoucherOrder;
import org.springframework.stereotype.Service;

@Service
public interface DianPingVoucherService extends IService<DianPingVoucherOrder> {

    DianPingVoucherOrder doDianPingTittle(String orderId,String tittle,String userId);

    Boolean doDPOrderToMysql(DianPingVoucherOrder dianPingVoucherOrder);

    Result selectAllDPOrder(String userid, int pageNum);

    Result selectDPOrderInRedis(String userid,int pageNum);

    Boolean doDPOrderToRedis(DianPingVoucherOrder dianPingVoucherOrder);

    Result selectCandoDPVoucherInRedis(String userid);

}
