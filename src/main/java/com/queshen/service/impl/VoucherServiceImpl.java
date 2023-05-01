package com.queshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Voucher;
import com.queshen.mapper.VoucherMapper;
import com.queshen.service.IVoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 卡券服务实现类
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Slf4j
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements IVoucherService {

    @Resource
    private VoucherMapper voucherMapper;

    @Override
    public Result queryVoucherById(String voucherId) {
        // 根据卡券id查询卡券
        Voucher voucher = voucherMapper.selectById(voucherId);

        if (voucher == null){
            return null;
        }

        return Result.ok(voucher);
    }

}
