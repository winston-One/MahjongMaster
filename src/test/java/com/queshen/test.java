package com.queshen;

import com.queshen.mapper.VoucherMapper;
import com.queshen.mapper.VoucherOrderMapper;
import com.queshen.pojo.bo.TimeRange;
import com.queshen.service.DianPingVoucherService;
import com.queshen.service.IVoucherOrderService;
import com.queshen.service.IVoucherService;
import com.queshen.utils.TimeRangeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author WinstonYv
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


    @Test
    public void test(){

    }

    @Test
    public void test2(){
        String date = "2023-4-24";
        String startAt = date + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("CTT"));
        Date conversionDate = null;
        try {
            conversionDate = sdf.parse(startAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = conversionDate.getTime();
        System.out.println(l);
    }

    @Test
    public void testUmsAdmin() {
        List<TimeRange> timeRanges = TimeRangeUtil.getTimeRanges("2022-12-16");
        for (TimeRange timeRange : timeRanges) {
            System.out.println(timeRange);
        }
    }

}
