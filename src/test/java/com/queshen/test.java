package com.queshen;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queshen.mapper.OrderMapper;
import com.queshen.mapper.TestMapper;
import com.queshen.pojo.bo.Temperature;
import com.queshen.pojo.bo.TimeRange;
import com.queshen.pojo.po.TestChildren;
import com.queshen.utils.TimeRangeUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    List<Temperature> temperatures = new ArrayList<>();
    @Test
    public void testChaZhi() {

        // 读取csv文件
        try {
            File file = new File("F:\\Temperature.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            // CSV文件的分隔符
            String DELIMITER = ",";
            // 按行读取
            String line;
            while ((line = br.readLine()) != null) {
                // 分割
                String[] columns = line.split(DELIMITER);
                Temperature temperature = new Temperature(
                        Integer.valueOf(columns[0]),
                        Double.valueOf(columns[1]),
                        Double.valueOf(columns[2]),Double.valueOf(columns[3]),
                        Double.valueOf(columns[4]));
                temperatures.add(temperature);
                // 打印行
                System.out.println("Temperature["+ String.join(",", columns) +"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(temperatures);
        // 插值算法
        InterpolationAlgorithm();
    }

    // 使用工具包commons-math3辅助实现
    private void InterpolationAlgorithm() {
        // 判断数据是否已经准备好
        if (temperatures.size() == 0 || temperatures == null) {
            return ;
        }

        // 准备插值
        SplineInterpolator interpolator = new SplineInterpolator();
        double[] xArray;
        double[] yArray;
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        for (Temperature temperature : temperatures) {
            x.add(temperature.getXNode());
            y.add(temperature.getYNode());
        }
        xArray = x.stream().mapToDouble(n -> n).toArray();
        yArray = y.stream().mapToDouble(m -> m).toArray();

        PolynomialSplineFunction insertMissingFunc = interpolator.interpolate(xArray, yArray);

        // 正常情况下所有点
        final int normalSize = 9999;
        Double current = x.get(0);
        List<Double> threeDimensional = new LinkedList<>();

        // 插值法进行填充
        for (int i = 0; i < x.size() && threeDimensional.size() < normalSize; ) {
            // 如果存在，则不需要插值
            if (current == x.get(i)) {
                threeDimensional.add(y.get(i));
                i++;
            } else {
                // 如果不存在，则插值
                double value = insertMissingFunc.value(current);
                threeDimensional.add(value);
            }
            current += 0.2;
        }
    }

}
