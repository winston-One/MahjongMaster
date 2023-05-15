package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.po.TestChildren;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WinstonYv
 * @create 2023/5/10 15:34
 * @Description: Man can conquer nature
 **/
@Mapper
public interface TestMapper extends BaseMapper<TestChildren> {

    List<TestChildren> getChildren(String parentId);
}
