package com.queshen.pojo.vo;

import com.queshen.pojo.po.DianPingVoucherOrder;
import lombok.Data;

@Data
public class DianPingVoucherVO {
    private DianPingVoucherOrder dianPingVoucherOrder;
    private String receiptCode;
    private Integer count;
}
