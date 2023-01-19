package com.dc.dynamic.schedule.config;

import com.dc.dynamic.schedule.annotation.DynamicScheduled;
import com.dc.dynamic.schedule.task.CustomCronTaskRegister;
import com.dc.dynamic.schedule.task.DcSchedulingRunnable;
import com.dc.dynamic.schedule.utils.StrUtil;
import com.sun.org.apache.xerces.internal.xinclude.XPointerSchema;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;

public class DynamicSchedulingAutoRegistryProcess implements BeanPostProcessor {


    @Autowired
    private ConfigurablePropertyResolver propertyResolver;

    @Autowired
    private CustomCronTaskRegister customCronTaskRegister;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods == null) return bean;
        for (Method method : methods) {
            DynamicScheduled dcsScheduled = AnnotationUtils.findAnnotation(method, DynamicScheduled.class);
            //初始化加载定时任务
            if (!ObjectUtils.isEmpty(dcsScheduled)) {
                String resolve = StrUtil.resolve(propertyResolver, dcsScheduled.cron());
                DcSchedulingRunnable schedulingRunnable = new DcSchedulingRunnable(bean,beanName,method.getName(),dcsScheduled.desc(),resolve);
                schedulingRunnable.setStatus(false);
                customCronTaskRegister.initCronTask(schedulingRunnable);
            }
        }
        return bean;
    }
    
    @Bean("dynamic-schedule-taskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //线程池使用的就是当前线程的大小
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("dynamicScheduleThreadPool-");
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
