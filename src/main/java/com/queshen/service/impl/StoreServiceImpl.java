package com.queshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.po.Store;
import com.queshen.mapper.StoreMapper;
import com.queshen.service.StoreService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Component
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
}
