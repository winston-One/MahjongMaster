package com.queshen.quartz.task;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author winston
 * @create 2022/11/22 23:25
 * @Description: Man can conquer nature
 **/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SendMsgJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        // 将执行定时任务的当前时间存储起来
        System.out.println("保存当前时间"+ dateString + "到数据库中");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO 执行发送消息的操作操作

    }
}
