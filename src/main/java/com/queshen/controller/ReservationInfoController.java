package com.queshen.controller;

import com.queshen.pojo.bo.Result;
import com.queshen.service.ReservationInfoService;
import com.queshen.pojo.vo.RoomInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 */
@Slf4j
@RestController
@RequestMapping("/reservation")
public class ReservationInfoController {

    @Resource
    private ReservationInfoService reservationInfoService;


    @GetMapping("/getAll")
    public Result getAllInfo(@RequestParam("storeId") String storeId,
                             @RequestParam("date") String date) {
        System.out.println(storeId);
        System.out.println(date);
        return reservationInfoService.getAllInfo(storeId, date);
    }


    @GetMapping("/get")
    public Result getInfoByRoom(@RequestParam("roomId") String roomId,
                                @RequestParam("date") String date) {
        System.out.println(roomId);
        System.out.println(date);
        return reservationInfoService.getInfoByRoom(roomId, date);
    }
}
