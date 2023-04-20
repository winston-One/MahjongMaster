package com.queshen.pojo.vo;

import com.queshen.pojo.po.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author VioletQin
 * @since 2022/11/21
 */
@Data
@AllArgsConstructor
@Builder
public class RoomInfoVO {
    /** 房间号*/
    private String roomId;

    /** 图片地址*/
    private String photoAdd;

    /** 房间名*/
    private String roomName;

    /** 服务tag*/
    private List<String> tag;

    /** 时间轴信息 0->未预定   1->已预定   2->时间已过 */
    private List<Integer> timeRanges;

    /** 每个小时的单价*/
    private BigDecimal pricePerHour;

    /** 是否空闲*/
    private Boolean isFree;
}
