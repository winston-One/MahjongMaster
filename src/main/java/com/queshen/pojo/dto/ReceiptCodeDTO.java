package com.queshen.pojo.dto;

import lombok.Data;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class ReceiptCodeDTO {

    // 用户id
    private String userId;

    // 券码
    private String receiptCode;

    // 需要验券的数量
    private Integer count;

}
