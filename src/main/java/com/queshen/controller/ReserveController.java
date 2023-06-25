package com.queshen.controller;

import com.queshen.anno.Log;
import com.queshen.constant.BusinessType;
import com.queshen.pojo.bo.Result;
import com.queshen.service.ReservationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 * 预约房间接口
 */
@Slf4j
@RestController
@RequestMapping("/reservation")
public class ReserveController {

    @Resource
    private ReservationInfoService reservationInfoService;

    // 查询出该门店所有可以预约的房间
    @GetMapping("/getAll")
//    @Log(content="用户查询预约房间",businessType = BusinessType.SELECT)
    public Result getAllInfo(@RequestParam("storeId") String storeId,
                             @RequestParam("date") String date) {
        return reservationInfoService.getAllInfo(storeId, date);
    }

    // 查询出该房间可以预约的24个时间段，分为当天和次日
    @GetMapping("/get")
//    @Log(content="用户查询预约房间",businessType = BusinessType.SELECT)
    public Result getInfoByRoom(@RequestParam("roomId") String roomId,
                                @RequestParam("date") String date) throws ParseException {
        System.out.println(roomId+date);
        return reservationInfoService.getInfoByRoom(roomId, date);
    }
}
