package com.queshen.pojo.vo;

import com.queshen.pojo.po.DianPingVoucherOrder;
import lombok.Data;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class DianPingVoucherVO {
    private DianPingVoucherOrder dianPingVoucherOrder;
    private String receiptCode;
    private Integer count;
}
