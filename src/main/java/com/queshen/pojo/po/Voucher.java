package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 卡券实体类
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@TableName("tb_voucher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voucher implements Serializable {

    // 卡券ID
    @TableId
    private String voucherId;

    // 卡券标题
    private String title;

    // 可用范围
    private String availableRange;

    // 价格
    private BigDecimal price;

    // 有效期
    private Integer term;

    // 状态
    private Integer vouStatus;

    // 原价
    private Integer originalPrice;

    // 使用时长
    private BigDecimal duration;

    //    @ApiModelProperty(value = "逻辑删除：0删除")
    private Integer isDelete;

    //    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    //    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

}
