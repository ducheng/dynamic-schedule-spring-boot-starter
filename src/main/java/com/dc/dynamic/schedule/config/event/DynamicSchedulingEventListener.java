package com.dc.dynamic.schedule.config.event;


import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.dc.dynamic.schedule.common.ConstantsPool;
import com.dc.dynamic.schedule.task.CustomCronTaskRegister;
import com.dc.dynamic.schedule.task.DcSchedulingRunnable;
import com.dc.dynamic.schedule.task.ScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * 动态定时任务监听器
 */
public class DynamicSchedulingEventListener implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(DynamicSchedulingEventListener.class);

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private EventBus eventBus;

    public DynamicSchedulingEventListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Autowired
    private CustomCronTaskRegister customCronTaskRegister;

    /**
     * 根据事件驱动来修改定时任务
     * @param object
     */
    @Subscribe
    @AllowConcurrentEvents
    public void updateSchedule(Object object){
        if (object instanceof  DynamicScheduleEvent) {
            DynamicScheduleEvent  scheduleEvent = (DynamicScheduleEvent) object;
            String taskId = scheduleEvent.getTaskId();
            if (ObjectUtils.isEmpty(taskId) ) {
                logger.error("taskId:{}不能为null",scheduleEvent.getTaskId());
            }
            ScheduledTask scheduledTask = ConstantsPool.TASK_CONCURRENT_HASH_MAP.get(taskId);
            if (ObjectUtils.isEmpty(scheduledTask)) {
                logger.error("taskId:{}没有找到对应的执行task,请检查",scheduleEvent.getTaskId());
            }
            if(scheduleEvent.getCancel()) {
                customCronTaskRegister.removeCronTask(taskId);
            } else {
                if (ObjectUtils.isEmpty(scheduleEvent.getCronExpression())) {
                    logger.error("cron表达式不能为空,请检查");
                }
                DcSchedulingRunnable dcSchedulingRunnable = ConstantsPool.SCHEDULING_RUNNABLE_MAP.get(taskId);
                //判断新的和旧的cron 表达式是否相同
                if (!dcSchedulingRunnable.getCronExpression().equals(scheduleEvent.getCronExpression())) {
                    dcSchedulingRunnable.setCronExpression(scheduleEvent.getCronExpression());
                    customCronTaskRegister.addCronTask(dcSchedulingRunnable,scheduleEvent.getCronExpression());
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eventBus.register(this);
    }
}
