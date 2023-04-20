package com.queshen.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderTime {
    private String storeId;
    private String roomId;
    private LocalDateTime startTme;
    private LocalDateTime endTime;
}
