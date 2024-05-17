package com.queshen.controller;

import cn.hutool.json.JSONUtil;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.*;
import com.queshen.pojo.vo.OrderSelectByUserVO;
import com.queshen.service.AppConstantsService;
import com.queshen.service.DianPingVoucherService;
import com.queshen.pojo.po.AppConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 * 美团验券的前端控制器
 */
@Log4j2
@RequestMapping("/DianPing")
@RestController
public class DianPingController {

    @Resource
    public DianPingVoucherService dianPingVoucherService;

    @Resource
    public AppConstantsService appConstantsService;

    @Resource
    public StringRedisTemplate stringRedisTemplate;

    /**
     * 验券接口
     * @param receiptCode [userId:用户id ，receiptCode：券码]
     */
    @PostMapping("/verification")
    public Result verification(@RequestBody ReceiptCodeDTO receiptCode){
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, String> requestParam = new HashMap<>();
//        AppConstants sessionAndRefresh = getSessionAndRefresh();
//        requestParam.put("app_key",AppConstants.APP_KEY);
//        requestParam.put("timestamp",format.format(System.currentTimeMillis()));
//        requestParam.put("format","json");
//        requestParam.put("sign_method",AppConstants.SIGN_METHOD_MD5);
//        requestParam.put("session",sessionAndRefresh.session);
//        requestParam.put("open_shop_uuid",AppConstants.OPEN_SHOP_UUID);
//        requestParam.put("receipt_code",receiptCode.getReceiptCode());
//        log.info(receiptCode);
//        //验证签名 将请求第三方接口所需要参数和app密钥加密成一个签名
//        requestParam.put("sign", SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
//        log.info(SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
//        //构造form
//        Form form = Form.form();
//        for(Map.Entry<String, String> entry : requestParam.entrySet()){
//            form.add(entry.getKey(), entry.getValue());
//        }
//        try {
//            String s = Request.Post("https://openapi.dianping.com/router/tuangou/receipt/prepare")
//                    .bodyForm(form.build())// 带三方接口请求体要是使用form-data形式
//                    .setHeader("Content-Type", ContentType.create("application/x-www-form-urlencoded", "UTF-8").toString())
//                    .execute().returnContent().asString();
//            log.info(s);
//            PreparemtRecordResultDTO preparemtRecordResult;
//            JSONObject jsonStr = JSONObject.parseObject(s);
//            preparemtRecordResult = JSONObject.toJavaObject(jsonStr,PreparemtRecordResultDTO.class);
//            if (preparemtRecordResult.code == 200){
//                PreparemtRecordDTO preparemtRecord = preparemtRecordResult.Data;
//                log.info(preparemtRecord);
//                DianPingVoucherOrder dianPingVoucherOrder = dianPingVoucherService.doDianPingTittle(receiptCode.getReceiptCode(), preparemtRecord.deal_title,receiptCode.getUserId());
//                if (dianPingVoucherOrder == null)
//                    return Result.fail("抱歉，目前暂时不支持该类型的券");
//                DianPingVoucherVO dianPingVoucherVO = new DianPingVoucherVO();
//                dianPingVoucherVO.setDianPingVoucherOrder(dianPingVoucherOrder);
//                dianPingVoucherVO.setReceiptCode(receiptCode.getReceiptCode());
//                dianPingVoucherVO.setCount(preparemtRecord.count);
//                return Result.ok(dianPingVoucherVO);
//            }
//            else {
//                String msg = preparemtRecordResult.msg;
//                Integer code = preparemtRecordResult.code;
//                return Result.fail(msg).code(code);
//            }
//        } catch (IOException e) {
//            log.info(e.toString());
//        }
        return Result.fail("系统错误");
    }

    // 二维码验券
    @PostMapping("/verificationByQRCode")
    public Result verificationByQRCode(@RequestBody ReceiptCodeDTO receiptCode){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, String> requestParam = new HashMap<>();
//        // 获取session和refresh_session
//        AppConstants sessionAndRefresh = getSessionAndRefresh();
//        // 以下是请求第三方验券接口所需要的参数，具体参数的
//        requestParam.put("app_key",AppConstants.APP_KEY);
//        requestParam.put("timestamp",format.format(System.currentTimeMillis()));
//        requestParam.put("format","json");// 请求参数的格式是json
//        requestParam.put("sign_method",AppConstants.SIGN_METHOD_MD5);
//        requestParam.put("session",sessionAndRefresh.session);
//        requestParam.put("open_shop_uuid",AppConstants.OPEM_SHOP_UUID);
//        requestParam.put("qr_code", receiptCode.getReceiptCode());
//        log.info(receiptCode);
//        //验证签名
//        requestParam.put("sign", SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
//        log.info(SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
//        //构造form
//        Form form = Form.form();
//        for(Map.Entry<String, String> entry : requestParam.entrySet()){
//            form.add(entry.getKey(), entry.getValue());
//        }
//        try {
//            // 请求美团验券接口，代入前面封装好的参数（app密钥，时间戳会话，店铺id等等）
//            String s = Request.Post("https://openapi.dianping.com/router/tuangou/receipt/scanprepare")
//                    .bodyForm(form.build())
//                    .setHeader("Content-Type", ContentType.create("application/x-www-form-urlencoded", "UTF-8").toString())
//                    .execute().returnContent().asString();
//            log.info(s);
//            PreparemtRecordResultDTO preparemtRecordResult;
//            // 将请求结果集进行JSON解析
//            JSONObject jsonStr = JSONObject.parseObject(s);
//            // 将结果集映射到PreparemtRecordResultDTO自定义实体对象中
//            preparemtRecordResult = JSONObject.toJavaObject(jsonStr,PreparemtRecordResultDTO.class);
//            if (preparemtRecordResult.code == 200){
//                PreparemtRecordDTO preparemtRecord = preparemtRecordResult.Data;
//                log.info(preparemtRecord);
//                DianPingVoucherOrder dianPingVoucherOrder = dianPingVoucherService.doDianPingTittle(receiptCode.getReceiptCode(), preparemtRecord.deal_title,receiptCode.getUserId());
//                if (dianPingVoucherOrder == null)
//                    return Result.fail("抱歉，目前暂时不支持该类型的券");
//                DianPingVoucherVO dianPingVoucherVO = new DianPingVoucherVO();
//                dianPingVoucherVO.setDianPingVoucherOrder(dianPingVoucherOrder);
//                dianPingVoucherVO.setReceiptCode(receiptCode.getReceiptCode());
//                return Result.ok(dianPingVoucherVO);
//            }
//            else {
//                String msg = preparemtRecordResult.msg;
//                Integer code = preparemtRecordResult.code;
//                return Result.fail(msg).code(code);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Result.fail("系统错误");
    }

    // 销券接口
    @PostMapping("/verificationWrittenOff")
    public Result verificationWrittenOff(@RequestBody ReceiptCodeDTO receiptCodeDTO) {
//        if (receiptCodeDTO.getUserId()==null)
//            return Result.fail("无用户名错误");
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, String> requestParam = new HashMap<>();
//        AppConstants sessionAndRefresh = getSessionAndRefresh();
//        requestParam.put("app_key",AppConstants.APP_KEY);
//        requestParam.put("timestamp",format.format(System.currentTimeMillis()));
//        requestParam.put("format","json");
//        requestParam.put("requestid", receiptCodeDTO.getUserId()+System.currentTimeMillis());
//        requestParam.put("sign_method",AppConstants.SIGN_METHOD_MD5);
//        requestParam.put("session",sessionAndRefresh.session);
//        requestParam.put("open_shop_uuid",AppConstants.OPEM_SHOP_UUID);
//        requestParam.put("receipt_code",receiptCodeDTO.getReceiptCode());
//        requestParam.put("count",receiptCodeDTO.getCount().toString());
//        requestParam.put("app_shop_account","mashangdao2020");
//        requestParam.put("app_shop_accountname","mashangdao");
//        //验证签名
//        requestParam.put("sign", SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
//        //构造form
//        Form form = Form.form();
//        for(Map.Entry<String, String> entry : requestParam.entrySet()){
//            form.add(entry.getKey(), entry.getValue());
//        }
//        //发起post请求
//        String s;
//        try {
//            s = Request.Post("https://openapi.dianping.com/router/tuangou/receipt/consume")
//                    .bodyForm(form.build())
//                    .setHeader("Content-Type", ContentType.create("application/x-www-form-urlencoded", "UTF-8").toString())
//                    .execute().returnContent().asString();
//            log.info(s);
//            JSONObject jsonStr = JSONObject.parseObject(s);
//            ConsumeResult consumeResult = JSONObject.toJavaObject(jsonStr, ConsumeResult.class);
//            log.info(consumeResult);
//            if(consumeResult.code == 200){
//                JSONArray data = consumeResult.Data;
//                String dataString = JsonUtils.toJson(data);
//                DianPingVorcherDTO dianPingVorcherDTO = JsonUtils.toBean(dataString, DianPingVorcherDTO.class);
//                log.info(dianPingVorcherDTO);
//                DianPingVoucherOrder dianPingVoucherOrder = dianPingVoucherService.doDianPingTittle(dianPingVorcherDTO.getOrder_id(), dianPingVorcherDTO.getDeal_title(),receiptCodeDTO.getUserId());
//                log.info(dianPingVoucherOrder);
//                if(dianPingVoucherService.doDPOrderToRedis(dianPingVoucherOrder))
//                    return Result.ok("销券成功，请在本平台进行预约使用该券");
//                else
//                    return Result.fail("存入出错");
//            }
//            else{
//                return Result.fail(consumeResult.msg).code(consumeResult.code);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Result.fail("系统错误");
    }


    /**
     * 查询所有美团券（订单）
     * @param orderSelectByUserVO [openId：用户id，pageNum:目前多少页]
     */
    @PostMapping("/selectAllDPOrder")
    public Result selectAllDPOrder(@RequestBody OrderSelectByUserVO orderSelectByUserVO){
        return dianPingVoucherService.selectAllDPOrder(orderSelectByUserVO.getOpenId(),orderSelectByUserVO.getPageNum());
    }

    /**
     * 查看可用美团券（在redis中的）
     * @param orderSelectByUserVO [openId:用户id pageNum：当前页数]
     */
    @PostMapping("/selectDPOrderInRedis")
    public Result selectDPOrderInRedis(@RequestBody OrderSelectByUserVO orderSelectByUserVO){
        return dianPingVoucherService.selectDPOrderInRedis(orderSelectByUserVO.getOpenId(),orderSelectByUserVO.getPageNum());
    }

    /**
     * 查看可用美团券（在redis中的）
     * @param  userId:用户id
     */
    @GetMapping("/selectCanDoDPOrderInRedis")
    public Result selectCanDoDPOrderInRedis(@RequestParam("userId") String userId){
        return dianPingVoucherService.selectCanDoDPVoucherInRedis(userId);
    }

    //获取session和refresh_session
    private  AppConstants getSessionAndRefresh(){
        String appKey = AppConstants.APP_KEY;
        // 会话数据是存储在Redis里的
        String s = stringRedisTemplate.opsForValue().get(appKey);
        DianPingSessionDTO dianPingSessionDTO = JSONUtil.toBean(s, DianPingSessionDTO.class);
        log.info(dianPingSessionDTO);
        AppConstants appConstants = new AppConstants();
        appConstants.setSession(dianPingSessionDTO.getAccess_token());
        appConstants.setRefresh_token(dianPingSessionDTO.getRefresh_token());
        log.info(appConstants);
        return appConstants;
    }

    // 设置session和refresh_session
    private void setSessionAndRefresh(DianPingSessionDTO dianPingSessionDTO){
        String key=AppConstants.APP_KEY;
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(dianPingSessionDTO));
    }

    // 更新Session 一个月调用一次，session的过期时间默认就是一个月，每个月的壹号00:30:00执行一次
//    @Scheduled(cron = "0 30 0 1 * ?")
//    public Result freshSession(){
//        String key=AppConstants.APP_KEY;
//        String s = stringRedisTemplate.opsForValue().get(key);
//        DianPingSessionDTO dianPingSessionDTO1 = JSONUtil.toBean(s, DianPingSessionDTO.class);
//        //java SDK的方式更新
//        DefaultOpenAPIClient openAPIClient = new DefaultOpenAPIClient();
//        log.info(dianPingSessionDTO1.getRefresh_token());
//        RefreshTokenRequest request = new RefreshTokenRequest(AppConstants.APP_KEY,AppConstants.APP_SECRET,dianPingSessionDTO1.getRefresh_token());
//        CustomerRefreshToken refreshToken = new CustomerRefreshToken(request);
//        CustomerRefreshTokenResponse response = openAPIClient.invoke(refreshToken);
//        if (response.getCode()!=200)
//            return Result.fail(response.getMsg()).code(response.getCode());
//        stringRedisTemplate.opsForValue().set("freshsession",JsonUtils.toJson(response));
//        DianPingSessionDTO dianPingSessionDTO=new DianPingSessionDTO();
//        BeanUtils.copyProperties(response,dianPingSessionDTO);
//        setSessionAndRefresh(dianPingSessionDTO);
//        return Result.ok(response);
//    }

    // 查询该美团号管理的门店范围
    @PostMapping("/selectStoreScope")
    public String selectStoreScope()  {
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, String> requestParam = new HashMap<>();
//        AppConstants sessionAndRefresh = getSessionAndRefresh();
//        requestParam.put("app_key",AppConstants.APP_KEY);
//        requestParam.put("timestamp",format.format(System.currentTimeMillis()));
//        requestParam.put("format","json");
//        requestParam.put("sign_method",AppConstants.SIGN_METHOD_MD5);
//        requestParam.put("session",sessionAndRefresh.session);
//        requestParam.put("v", "1");
//        requestParam.put("bid", "857d16528a9c1abc3329fd390ad0b488");
//        requestParam.put("open_shop_uuid",AppConstants.OPEM_SHOP_UUID);
//        //验证签名
//        requestParam.put("sign", SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
//        log.info(SignUtil.generateSign(requestParam,AppConstants.APP_SECRET,AppConstants.SIGN_METHOD_MD5));
//        try {
//            String s = Request
//                    .Get("https://openapi.dianping.com/router/oauth/session/scope?" + RequestUtil.mapToGetParam(requestParam))
//                    .execute()// 执行get请求
//                    .returnContent()// 返回结果集
//                    .asString();// 转化string
//            log.info(s);
//            return s;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
