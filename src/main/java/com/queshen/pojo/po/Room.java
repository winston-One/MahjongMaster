package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@TableName("tb_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * 房间 状态
     */
    private Integer status;
    /**
     * 房间 价格
     */
    private BigDecimal price;
    /**
     * 房间 图片地址
     */
    private String image;


    /**
     * 备注
     */
    @TableField("room_remarks")
    private String remarks;

    // 数据库对应的字段也要新增，并且改is_deleted数据为0
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    public Room(String roomName, String storeId, Integer status, BigDecimal price, String remarks) {
        this.roomName = roomName;
        this.storeId = storeId;
        this.status = status;
        this.price = price;
        this.remarks = remarks;
    }
}
