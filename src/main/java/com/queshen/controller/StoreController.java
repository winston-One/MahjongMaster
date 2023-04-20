package com.queshen.controller;

import com.queshen.pojo.bo.Result;
import com.queshen.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    public StoreService storeService;

    @PostMapping("selectAllStore")
    public Result selectAllStore(){
        return storeService.selectAllStore();
    }

}
