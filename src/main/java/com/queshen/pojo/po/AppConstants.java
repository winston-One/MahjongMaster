package com.queshen.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 公共参数的工具类
 * Appkey 84d42b2912205c74
 * Secret 6d587069d22ca42c97ce148d41375e4503c3980d
 * @author WinstonYv
 * @create 2022/11/14
 * @Description:
 **/
@Data
@Component
@TableName("tb_dianpingsession")
public class AppConstants {

    @TableField("dp_session")
    public  String session;

    @TableField("dp_refresh_token")
    public  String refresh_token;

    @TableId("dp_id")
    public String id;

    public final static String APP_KEY="84d42b2912205c74";

    public final static String SIGN_METHOD_MD5="MD5";

    public final static String SIGN_METHOD_HMAC="HMAC";

    public final static String CHAR_SECRET="UTF-8";

    public final static String APP_SECRET="6d587069d22ca42c97ce148d41375e4503c3980d";

    public final static String OPEM_SHOP_UUID="d1c677ea16675d9b8883cc2e305c95d6";

    public final static String TIMESTAMP_PATTERN="yyyy-mm-dd HH:mm:ss";

    public final static String DATE_PATTERN="yyyy-MM-dd";

    public final static String HOST="https//openapi.dianping.com";


}
