package com.queshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.queshen.pojo.dto.VoucherOrderDTO;
import com.queshen.pojo.dto.VoucherSuitDTO;
import com.queshen.pojo.po.VoucherOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WinstonYv
 * @since 2022/11/14
 **/
@Mapper
public interface VoucherOrderMapper extends BaseMapper<VoucherOrder> {

    List<VoucherOrderDTO> listVoucherOrder(@Param("user_id") String userId);

    List<VoucherSuitDTO> voucherJudgement(@Param("user_id") String userId,
                                          @Param("room_name") String roomName,
                                          @Param("duration") BigDecimal duration);

}
