package com.queshen.service;

import com.queshen.pojo.bo.Result;

/**
 * @author VioletQin
 * @since 2022/12/2
 */
public interface ReservationInfoService {

    /**
     * 获取某个店面某一天的所有房间的预约信息
     * @param storeId
     * @param date
     * @return
     */
    Result getAllInfo(String storeId, String date);

    /**
     * 获取某个房间某一天的具体预约信息
     * @param roomId
     * @param date
     * @return
     */
    Result getInfoByRoom(String roomId, String date);
}
