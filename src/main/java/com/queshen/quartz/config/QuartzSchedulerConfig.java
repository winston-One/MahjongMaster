package com.queshen.quartz.config;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author winston
 * @create 2022/11/22 23:19
 * @Description: Man can conquer nature
 **/
@Configuration
public class QuartzSchedulerConfig {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private MyAdaptableJobFactory myAdaptableJobFactory;

    public <E extends Job> void startJob(String name, String group, String cron, Class<E> job)
            throws SchedulerException {
        // JobDetail 是具体Job实例
        JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(name, group).build();
        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // CronTrigger表达式触发器 继承于Trigger,TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(name, group)
                .withSchedule(cronScheduleBuilder) // 每隔多长时间执行一次
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);// 返回值是一个Date，调度器的开始时间
        // 启动监听器
        /** 添加Scheduler 监听器
         *scheduler.getListenerManager().addJobListener(new JobListener(),EverythingMatcher.allJobs());//注册全局
         * scheduler.getListenerManager().addJobListener(new JobListener(), KeyMatcher.keyEquals(JobKey.jobKey(name,group)));// 注册局部的job
         */
        scheduler.setJobFactory(myAdaptableJobFactory);
        scheduler.start();
    }

}
