package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.admin.RoomVo;
import com.queshen.pojo.po.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    List<RoomVo> getData(String storeId);
}
