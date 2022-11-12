package com.dc.dynamic.schedule.annotation;

import com.dc.dynamic.schedule.config.DynamicSchedulingAutoRegistryProcess;
import com.dc.dynamic.schedule.web.DynamicScheduleControllerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否开启使用动态定时任务的注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DynamicSchedulingAutoRegistryProcess.class, DynamicScheduleControllerRegister.class})
public @interface EnableDynamicScheduling {
}
