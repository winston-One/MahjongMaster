package com.queshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.queshen.pojo.po.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
@Service
@Transactional
public interface IRoomService extends IService<Room> {

}
