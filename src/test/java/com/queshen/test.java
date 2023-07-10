package com.queshen;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queshen.mapper.TestMapper;
import com.queshen.pojo.po.TestChildren;
import com.queshen.service.AppConstantsService;
import com.queshen.service.DianPingVoucherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author WinstonYv
 * @create 23:25
 * @Description:
 **/

@RunWith(SpringRunner.class)
// 配置websocket，否则报错Error creating bean with name 'serverEndpointExporter' defined in class path
// 出现这个错的原因是在部署项目的时候,项目中含有websocket的@ServerEndpoint注解的时候,
// 如果项目是springboot项目,去除内置tomcat的时候会把websocket的包也给删除掉,所以需要手动在测试类加上这个包，保证测试环境可以使用
@SpringBootTest(classes = MainApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    public DianPingVoucherService dianPingVoucherService;

    @Autowired
    public AppConstantsService appConstantsService;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    List<TestChildren> tests = new ArrayList<>(); // 最终展现给前端的数据集

    @Test
    public void testChilder() {
        // 先查出头结点，也就是parentId为-1的数据
        QueryWrapper<TestChildren> queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", -1);
        DFS(testMapper.selectList(queryWrapper));
        System.out.println(tests);// 将前端的数据打印出来
    }
    // 深度优先遍历
    public void DFS(List<TestChildren> children) {
        int index = -1;
        for (TestChildren test : children) {
            // 查出该节点的子节点
            List<TestChildren> childrenList = testMapper.getChildren(test.getId());
            index = index + 1;
            if (childrenList.size() == 0){
                continue;
            }
            // 设置当前节点的子节点数据
            children.get(index).setChildren(childrenList);
            DFS(childrenList);
        }
        // 将最终的数据放回tests中
        tests = children;
    }
}
