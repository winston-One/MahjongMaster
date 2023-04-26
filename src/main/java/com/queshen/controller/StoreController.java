package com.queshen.controller;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource(name = "storeListCache")
    LoadingCache<String, List<Store>> storeListCache;

    // 查询出所有的商店
    @PostMapping("selectAllStore")
    public Result selectAllStore(){
        // 会直接查询本地缓存的门店信息，缓存本店信息类为config/StoreCache，如果找不到就会去数据库中查找，去数据库中查找的路基在StoreCache中
        List<Store> storeList = storeListCache.get("store");// 以store作为键值，第一次查询是查不到的，需要会自动查找数据库然后将数据放到键值为store的字典中
        return Result.ok(storeList);
    }

}
