package com.queshen.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public class OrderTime {

    private String storeId;

    private String roomId;

    private LocalDateTime startTme;

    private LocalDateTime endTime;

}
