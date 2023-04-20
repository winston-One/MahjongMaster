package com.queshen.pojo.dto;

import lombok.Data;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class ReceiptCodeDTO {

    private String userId;

    private String receiptCode;

    private Integer count;

}
