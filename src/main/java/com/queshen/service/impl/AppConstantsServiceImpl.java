package com.queshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.mapper.AppConstantsMapper;
import com.queshen.service.AppConstantsService;
import com.queshen.utils.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class AppConstantsServiceImpl extends ServiceImpl<AppConstantsMapper, AppConstants> implements AppConstantsService {
}
