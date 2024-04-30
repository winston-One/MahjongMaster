package com.queshen.pojo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomVo {

    private String roomId;

    private String roomName;

    private String storeId;

    private Integer status;

    private BigDecimal price;

    private String image;

    private String remarks;

    private String storeName;
}
