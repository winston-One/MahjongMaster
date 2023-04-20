package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.po.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VioletQin
 * @since 2022/12/2
 */
@Service
@Transactional
public interface IRoomService extends IService<Room> {

}
