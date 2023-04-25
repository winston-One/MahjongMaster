package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author winston
 * @create 2023/4/21 12:12
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoRepositoryBean
@ToString
@TableName("t_comment")
public class Comment {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
}
