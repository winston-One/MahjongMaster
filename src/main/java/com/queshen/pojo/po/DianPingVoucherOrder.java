package com.queshen.pojo.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.queshen.config.ListToStringHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@TableName(value = "tb_dpvoucherorder",autoResultMap = true)
public class DianPingVoucherOrder {

    private static final long serialVersionUID = 1L;

    //订单id
    @TableId("dporder_id")
    private String id;

    //用户id
    @TableField("user_id")
    private String userId;

    //价格
    private BigDecimal price;

    //时长
    @TableField("dporder_duration")
    private BigDecimal duration;

    //适用房型  List中填大 中 小 所有房型
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = ListToStringHandler.class )
    private List<String> room;

    //备注
    @TableField("postscript")
    private String PS;

    //状态 1已经使用 0未使用
    @TableField("dporder_status")
    private Integer status;
}
