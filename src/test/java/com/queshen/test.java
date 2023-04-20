package com.queshen;

import com.queshen.pojo.dto.VoucherOrderDTO;
import com.queshen.pojo.po.Voucher;
import com.queshen.pojo.po.VoucherOrder;
import com.queshen.mapper.VoucherMapper;
import com.queshen.mapper.VoucherOrderMapper;
import com.queshen.service.DianPingVoucherService;
import com.queshen.service.ICalc;
import com.queshen.service.IVoucherOrderService;
import com.queshen.service.IVoucherService;
import com.queshen.utils.TimeRange;
import com.queshen.utils.TimeRangeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.query;

/**
 * @author winston
 * @create 23:25
 * @Description:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Resource
    private VoucherMapper voucherMapper;
    @Resource
    private VoucherOrderMapper voucherOrderMapper;

    @Resource
    private DianPingVoucherService dianPingVoucherService;

    @Resource
    private IVoucherOrderService voucherOrderService;

    @Resource
    private IVoucherService voucherService;

    @Autowired
    private Map<String, ICalc> calcMap;

    @Test
    public void test(){
        ICalc calc = calcMap.getOrDefault("sub", null);
        if (null == calc) {
            System.out.println("没有找到正确的策略");
        }
        int i = 1;
        int j = 2;
        System.out.println(calc.operation(i, j) + "");
    }

    @Test
    public void test2(){
        String id ="o2eui5ZuZQt2eEsO7lyq0psWFXYg";
        List<VoucherOrderDTO> voucherOrderDTOS = voucherOrderMapper.listVoucherOrder(id);
        voucherOrderDTOS.forEach(System.out::println);
    }

    @Test
    public void testUmsAdmin() {
        List<TimeRange> timeRanges = TimeRangeUtil.getTimeRanges("2022-12-16");
        for (TimeRange timeRange : timeRanges) {
            System.out.println(timeRange);
        }
    }

}
