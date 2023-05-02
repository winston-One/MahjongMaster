package com.queshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.mapper.ChatMsgMapper;
import com.queshen.pojo.po.ChatMsg;
import com.queshen.service.IChatMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author winston
 * @create 2023/5/2 11:56
 * @Description: Man can conquer nature
 **/
@Slf4j
@Service
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg> implements IChatMsgService {

}
