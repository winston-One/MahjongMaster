package com.queshen.pojo.admin;

import com.queshen.pojo.po.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoreVo {

    private String storeId;

    private String storeName;

    private Integer roomSum;

    private Integer status;

    private String address;

    private String phone;

    private List<Room> roomList;

    private String wifi;

    private String wifiPassword;
}
