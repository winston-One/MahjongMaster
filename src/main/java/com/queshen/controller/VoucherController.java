package com.queshen.controller;

import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Voucher;
import com.queshen.service.IVoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 */
@Slf4j
@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private IVoucherService voucherService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增卡券功能
     * @Param voucher 卡券信息
     * @return 卡券id
     */
    @PostMapping("savevoucher")
    public Result addVoucher(@RequestBody Voucher voucher) {
        voucherService.save(voucher);
        return Result.ok(voucher.getVoucherId());
    }

    /**
     * 查询所有卡券列表功能
     * @Param voucher
     * @return 卡券信息list
     */
    @PostMapping("/allvoucher")
    public Result queryAllVoucher(){

        return voucherService.queryAllVoucher();
    }

    /**
     * 根据卡券id查询卡券功能
     * @Param voucherId
     * @return 卡券信息
     */
    @GetMapping("/selectvoubyid")
    public Result queryVoucherById(@RequestParam("voucherId") String voucherId){

        return voucherService.queryVoucherById(voucherId);
    }

    /**
     * 根据ID修改卡券功能
     * @Param voucher
     * @Param 影响行数
     */
    @PostMapping("/updatevoucher")
    public Result updateVoucher(@RequestBody Voucher voucher){
        int result = voucherService.getBaseMapper().updateById(voucher);
        return Result.ok(result);
    }

    /**
     * 根据卡券ID删除功能
     * @param voucherId
     * @Return 影响行数
     */
    @PostMapping("/deletevoucher")
    public Result deleteVoucherById(@RequestParam("voucherId") String voucherId){
        int result = voucherService.getBaseMapper().deleteById(voucherId);
        return Result.ok(result);
    }

}
