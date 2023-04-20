package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@TableName("tb_room")
@Data
public class Room {

    /**
     * 房间ID
     */
    @TableId
    private String roomId;
    /**
     * 房间名
     */
    private String roomName;
    /**
     * 店面号
     */
    private String storeId;
    /**
     * 房间状态
     */
    private Integer status;
    /**
     * 房间价格
     */
    private BigDecimal price;
    /**
     * 房间图片地址
     */
    private String image;
    /**
     * 备注
     */
    @TableField("room_remarks")
    private String remarks;
}
