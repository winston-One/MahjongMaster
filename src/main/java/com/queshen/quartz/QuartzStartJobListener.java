package com.queshen.quartz;

import com.queshen.quartz.config.QuartzSchedulerConfig;
import com.queshen.quartz.task.SendMsgJob;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author winston
 * @create 2022/11/22 23:45
 * @Description: Man can conquer nature
 **/
@Configuration
public class QuartzStartJobListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuartzSchedulerConfig quartzScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent args) {
        //  每天凌晨0点会发送消息给用户进行预约和缓存房间数据
        try {
            quartzScheduler.startJob("sendMsgJob", "group1", "0 0 0 * * ?", SendMsgJob.class);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
