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
 * @author WinstonYv
 * @since 2022/11/14
 */
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
