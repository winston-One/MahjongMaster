package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.dto.VoucherOrderDTO;
import com.queshen.pojo.dto.VoucherSuitDTO;
import com.queshen.pojo.po.VoucherOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 **/
public interface VoucherOrderMapper extends BaseMapper<VoucherOrder> {

//    @Select("Select t2.`voucher_id`,order_status,title,available_range,price,term,t2.`id`,pay_time,t2.`create_time`\n" +
//            "from tb_voucher t1 left join tb_voucher_order t2 ON t1.`voucher_id` = t2.`voucher_id`\n" +
//            "where t2.`user_id` = '${user_id}'")
    List<VoucherOrderDTO> listVoucherOrder(@Param("user_id") String userId);

//    @Select("SELECT t2.`voucher_id`,title,available_range,price,duration\n" +
//            "FROM tb_voucher t1 LEFT JOIN tb_voucher_order t2 ON t1.`voucher_id` = t2.`voucher_id`\n" +
//            "WHERE t2.`user_id` = '${user_id}' AND available_range LIKE '%${room_name}%' AND duration <= ${duration}")
    List<VoucherSuitDTO> voucherJudgement(@Param("user_id") String userId, @Param("room_name") String roomName, @Param("duration") BigDecimal duration);

}
