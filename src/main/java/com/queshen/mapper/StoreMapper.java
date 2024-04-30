package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.admin.StoreVo;
import com.queshen.pojo.po.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
@Mapper
public interface StoreMapper extends BaseMapper<Store> {
    List<StoreVo> getAllStore();
}
