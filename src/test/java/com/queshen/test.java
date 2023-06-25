package com.queshen;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queshen.mapper.OrderMapper;
import com.queshen.mapper.TestMapper;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.bo.Temperature;
import com.queshen.pojo.bo.TimeRange;
import com.queshen.pojo.dto.DianPingSessionDTO;
import com.queshen.pojo.dto.PreparemtRecordDTO;
import com.queshen.pojo.dto.PreparemtRecordResultDTO;
import com.queshen.pojo.dto.ReceiptCodeDTO;
import com.queshen.pojo.po.AppConstants;
import com.queshen.pojo.po.DianPingVoucherOrder;
import com.queshen.pojo.po.TestChildren;
import com.queshen.pojo.vo.DianPingVoucherVO;
import com.queshen.service.AppConstantsService;
import com.queshen.service.DianPingVoucherService;
import com.queshen.service.ReservationInfoService;
import com.queshen.utils.SignUtil;
import com.queshen.utils.TimeRangeUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private ReservationInfoService reservationInfoService;

    @Autowired
    public DianPingVoucherService dianPingVoucherService;

    @Autowired
    public AppConstantsService appConstantsService;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Test
    public void testReservation(){
//        reservationInfoService.getInfoByRoom("001","2023-06-26");
        ReceiptCodeDTO receiptCode = new ReceiptCodeDTO("o2eui5ZuZQt2eEsO7lyq0psWFXYg","9188918103",1);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> requestParam = new HashMap<>();
        AppConstants sessionAndRefresh = getSessionAndRefresh();
        requestParam.put("app_key",AppConstants.APP_KEY);
        requestParam.put("timestamp",format.format(System.currentTimeMillis()));
        requestParam.put("format","json");
        requestParam.put("sign_method",AppConstants.SIGN_METHOD_MD5);
        requestParam.put("session",sessionAndRefresh.session);
        requestParam.put("open_shop_uuid",AppConstants.OPEM_SHOP_UUID);
        requestParam.put("receipt_code",receiptCode.getReceiptCode());
        //验证签名 将请求第三方接口所需要参数和app密钥加密成一个签名
        requestParam.put("sign", SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
    //构造form
        Form form = Form.form();
        for(Map.Entry<String, String> entry : requestParam.entrySet()){
            form.add(entry.getKey(), entry.getValue());
        }

        try {
            String s = Request.Post("https://openapi.dianping.com/router/tuangou/receipt/prepare")
                    .bodyForm(form.build())// 带三方接口请求体要是使用form-data形式
                    .setHeader("Content-Type", ContentType.create("application/x-www-form-urlencoded", "UTF-8").toString())
                    .execute().returnContent().asString();
            PreparemtRecordResultDTO preparemtRecordResult;
            JSONObject jsonStr = JSONObject.parseObject(s);
            preparemtRecordResult = JSONObject.toJavaObject(jsonStr,PreparemtRecordResultDTO.class);
            System.out.println(preparemtRecordResult);
            if (preparemtRecordResult.code == 200){
                PreparemtRecordDTO preparemtRecord = preparemtRecordResult.Data;
                DianPingVoucherOrder dianPingVoucherOrder = dianPingVoucherService.doDianPingTittle(receiptCode.getReceiptCode(), preparemtRecord.deal_title,receiptCode.getUserId());

                DianPingVoucherVO dianPingVoucherVO = new DianPingVoucherVO();
                dianPingVoucherVO.setDianPingVoucherOrder(dianPingVoucherOrder);
                dianPingVoucherVO.setReceiptCode(receiptCode.getReceiptCode());
                dianPingVoucherVO.setCount(preparemtRecord.count);
                System.out.println(dianPingVoucherVO);
            }
            else {
                String msg = preparemtRecordResult.msg;
                Integer code = preparemtRecordResult.code;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取session和refresh_session
    private  AppConstants getSessionAndRefresh(){
        String appKey = AppConstants.APP_KEY;
        // 会话数据是存储在Redis里的
        String s = stringRedisTemplate.opsForValue().get(appKey);
        DianPingSessionDTO dianPingSessionDTO = JSONUtil.toBean(s, DianPingSessionDTO.class);
        AppConstants appConstants = new AppConstants();
        appConstants.setSession(dianPingSessionDTO.getAccess_token());
        appConstants.setRefresh_token(dianPingSessionDTO.getRefresh_token());
        return appConstants;
    }
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
