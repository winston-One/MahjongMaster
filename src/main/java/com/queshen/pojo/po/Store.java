package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@TableName("tb_store")
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    private static final long serialVersionUID = 1L;

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

    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    public Store(Integer status, String address, String phone, String storeName, String wifi, String wifiPassword) {
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.storeName = storeName;
        this.wifi = wifi;
        this.wifiPassword = wifiPassword;
    }
}
