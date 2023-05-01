package com.queshen.quartz.listens;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author winston
 * @create 2022/11/23 15:24
 * @Description: Man can conquer nature
 **/
public class JobListener extends JobListenerSupport {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Logger getLog() {
        return super.getLog();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        super.jobToBeExecuted(context);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        super.jobExecutionVetoed(context);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        super.jobWasExecuted(context, jobException);
    }

    @Override
    public String getName() {
        return null;
    }
}
