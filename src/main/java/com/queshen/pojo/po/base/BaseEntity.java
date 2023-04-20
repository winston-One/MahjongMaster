package com.queshen.pojo.po.base;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类基类
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
public abstract class BaseEntity implements Serializable {

//    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

//    @ApiModelProperty(value = "创建人")
    private String createUser = "0";

//    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

//    @ApiModelProperty(value = "修改人")
    private String updateUser;

//    @ApiModelProperty(value = "逻辑删除：0删除")
    private Integer isDelete;
}
