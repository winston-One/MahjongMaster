package com.queshen.pojo.vo;

import com.queshen.pojo.po.DianPingVoucherOrder;
import lombok.Data;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class DianPingVoucherVO {

    // 美团券实体
    private DianPingVoucherOrder dianPingVoucherOrder;

    private String receiptCode;

    private Integer count;
}
