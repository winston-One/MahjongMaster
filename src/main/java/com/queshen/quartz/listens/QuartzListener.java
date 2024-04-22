//package com.queshen.quartz.listens;
//
//import com.queshen.quartz.config.QuartzManager;
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.ContextRefreshedEvent;
//
///**
// * @author WinstonYv
// * @create 2022/11/23 15:24
// * @Description: Man can conquer nature
// * 定时任务监听器
// **/
//@Slf4j
//@Configuration
//public class QuartzListener {//} implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Autowired
//    private QuartzManager quartzManager;
//
//    // 注入scheduler
//    @Bean
//    public Scheduler scheduler() throws SchedulerException {
//        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
//        return schedulerFactoryBean.getScheduler();
//    }
//
//    // 启动quartz
////    @Override
////    public void onApplicationEvent(ContextRefreshedEvent event) {
////        try {
////            quartzManager.startJob();
////            log.info("任务已经启动...");
////        } catch (SchedulerException e) {
////            e.printStackTrace();
////        }
////    }
//
//}
