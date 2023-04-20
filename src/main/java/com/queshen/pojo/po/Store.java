package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_store")
public class Store {
    @TableId
    private String storeId;
    private Integer roomSum;
    @TableField("store_status")
    private Integer status;
    private String empId;
    private String address;
    private String phone;
    private String storeName;
    private String wifi;
    private String wifiPassword;
}
