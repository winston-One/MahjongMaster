package com.queshen.quartz.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 定时任务的实例化是被AdaptableJobFactory通过反射的方式来实例化并没有被Spring实例化。
 * 那么我们只要在执行任务调度之前，将相应的对象注入到Spring MVC中即可。
 * @author winston
 * @create 2022/11/22 23:31
 * @Description: Man can conquer nature
 **/
@Component
public class MyAdaptableJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    /**该方法需要将实例化的任务对象手动的添加到springIOC 容器中并且完成对象的注入 */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {

        Object obj = super.createJobInstance(bundle);
        // 将obj 对象添加Spring IOC容器中，并完成注入
        this.autowireCapableBeanFactory.autowireBean(obj);
        return obj;
    }

    /**
     * 创建和初始化Quartz调度器
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }

}
