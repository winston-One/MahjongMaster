package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Voucher;

/**
 * 卡券相关服务
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public interface IVoucherService extends IService<Voucher> {

    Result queryAllVoucher();

    Result queryVoucherById(String voucherId);
}
