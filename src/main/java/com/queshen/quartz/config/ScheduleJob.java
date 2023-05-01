package com.queshen.quartz.config;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务
 * @author winston
 * @create 2022/12/5 23:27
 * @Description: Man can conquer nature
 **/
@Slf4j
public class ScheduleJob extends QuartzJobBean {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		//数据库保存执行记录

		//任务开始时间
		long startTime = System.currentTimeMillis();

		try {
			//执行任务
			logger.debug("任务准备执行，任务ID：");

			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			//任务状态    0：成功    1：失败

		} catch (Exception e) {
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			//任务状态    0：成功    1：失败

		}finally {

		}
    }
}
