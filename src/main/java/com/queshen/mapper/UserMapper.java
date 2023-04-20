package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
