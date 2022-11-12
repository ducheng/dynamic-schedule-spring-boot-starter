package com.dc.dynamic.schedule.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.dc.dynamic.schedule.annotation.DynamicScheduled;
import com.dc.dynamic.schedule.config.event.DynamicScheduleService;
import com.dc.dynamic.schedule.config.event.DynamicSchedulingEventListener;
import com.dc.dynamic.schedule.task.CustomCronTaskRegister;
import com.dc.dynamic.schedule.task.DcSchedulingRunnable;
import com.dc.dynamic.schedule.utils.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.concurrent.*;

public class DynamicSchedulingAutoRegistryProcess implements BeanPostProcessor {

    /**
     *  设置事件驱动线程池的名字
     */
    private static final ThreadFactory factory  = new ThreadFactoryBuilder().setNameFormat("DynamicScheduling").build();


    @Autowired
    private ConfigurablePropertyResolver propertyResolver;

    @Autowired
    private CustomCronTaskRegister customCronTaskRegister;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods == null) return bean;
        for (Method method : methods) {
            DynamicScheduled dcsScheduled = AnnotationUtils.findAnnotation(method, DynamicScheduled.class);
            //初始化加载定时任务
            if (!ObjectUtils.isEmpty(dcsScheduled)) {
                String resolve = StrUtil.resolve(propertyResolver, dcsScheduled.cron());
                DcSchedulingRunnable schedulingRunnable = new DcSchedulingRunnable(bean,beanName,method.getName(),dcsScheduled.desc(),resolve);
                customCronTaskRegister.addCronTask(schedulingRunnable,resolve);
            }
        }
        return bean;
    }



    /**
     * 防止出现EventBus重复注册bean
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public EventBus executor() {
        BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(20);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,30, TimeUnit.SECONDS,workQueue,factory);
        return new AsyncEventBus(threadPoolExecutor);
    }

    /**
     * 当EventBus 存在的时候才注入 DynamicScheduling的监听器
     * @return
     */
    @Bean
    public DynamicSchedulingEventListener dcDynamicSchedulingEventListener(EventBus eventBus) {
        return new DynamicSchedulingEventListener(eventBus);
    }


    @Bean("dynamic-schedule-taskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //线程池使用的就是当前线程的大小
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("DynamicScheduleThreadPool-");
        return taskScheduler;
    }

    @Bean
    public CustomCronTaskRegister getCustomCronTaskRegister() {
        return new CustomCronTaskRegister();
    }

    @Bean
    public DynamicScheduleService DcDynamicScheduleService() {
        return new DynamicScheduleService();
    }

}
