package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Voucher;

/**
 * 卡券相关服务
 */
public interface IVoucherService extends IService<Voucher> {

    Result queryAllVoucher();

    Result queryVoucherById(String voucherId);
}
