package com.queshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.mapper.AppConstantsMapper;
import com.queshen.service.AppConstantsService;
import com.queshen.pojo.po.AppConstants;
import org.springframework.stereotype.Component;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Component
public class AppConstantsServiceImpl extends ServiceImpl<AppConstantsMapper, AppConstants> implements AppConstantsService {
}
