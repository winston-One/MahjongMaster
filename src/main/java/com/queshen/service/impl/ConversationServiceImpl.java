package com.queshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.queshen.mapper.ConversationMapper;
import com.queshen.pojo.po.Conversation;
import com.queshen.service.IConversationService;
import org.springframework.stereotype.Service;

/**
 * @author WinstonYv
 * @create 2023/5/2 13:11
 * @Description: Man can conquer nature
 **/
@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements IConversationService {
}
