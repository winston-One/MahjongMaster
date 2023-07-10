package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.po.LogInfo;

/**
 * @author WinstonYv
 * @create 2022/11/22 17:48
 * @Description: Man can conquer nature
 **/
public interface ILogInfoService extends IService<LogInfo> {

    void saveOperateLog(LogInfo operateLog);

}
