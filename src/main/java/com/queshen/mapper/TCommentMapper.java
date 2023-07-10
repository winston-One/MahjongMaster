package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.po.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WinstonYv
 * @create 2023/5/2 11:54
 * @Description: Man can conquer nature
 **/
@Mapper
public interface TCommentMapper extends BaseMapper<Comment> {

    List<Comment> getChildren(Integer parentId);

}
