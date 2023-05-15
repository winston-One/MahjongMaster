package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author WinstonYv
 * @create 2023/5/10 15:10
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@TableName("t_test")
public class TestChildren {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String name;

    private String parentId;

    @TableField(exist = false)
    private List<TestChildren> children;

}
