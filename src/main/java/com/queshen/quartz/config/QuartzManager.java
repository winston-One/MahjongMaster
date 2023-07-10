package com.queshen.quartz.config;

import com.queshen.quartz.task.ClearExpireOrderJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author WinstonYv
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 * 定义任务描述和具体的执行时间
 */
@Configuration
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    // 开始执行定时器
    public void startJob() throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定其job只能是实现Job接口的实例
        JobDetail jobDetail = JobBuilder
                .newJob(ClearExpireOrderJob.class)
                .withDescription("任务描述：清除过期订单")// 任务描述
                .storeDurably() // 每次任务执行后进行存储
                .withIdentity("job1", "group1")
                .build();
        // 凌晨00:00触发
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * ? * *");
        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .forJob(jobDetail) // 绑定工作任务
                .withIdentity("job1", "group1")
                .withSchedule(cronScheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        scheduler.start();
    }

    // =======================以下方法通过controller自行调用，主要是通过接口去触发====================================
    // 暂停所有任务
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    // 暂停某个任务
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    // 恢复所有任务
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    // 恢复某个任务
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
    }

}