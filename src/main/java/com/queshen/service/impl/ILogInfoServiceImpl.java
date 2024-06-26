package com.queshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.mapper.LogMapper;
import com.queshen.pojo.po.LogInfo;
import com.queshen.service.ILogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author WinstonYv
 * @create 2022/11/22 17:49
 * @Description: Man can conquer nature
 **/
@Service
@Slf4j
public class ILogInfoServiceImpl extends ServiceImpl<LogMapper, LogInfo> implements ILogInfoService {

    @Resource
    LogMapper logMapper;

    // 异步执行，避免因为写入磁盘耗时导致响应用户超时
    public void saveOperateLog(LogInfo operateLog){
        logMapper.insert(operateLog);
    }
}
