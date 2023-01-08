package com.dc.dynamic.schedule.task;


import com.dc.dynamic.schedule.common.ConstantsPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import javax.annotation.Resource;


/**
 *  定时任务注册器
 */
public class CustomCronTaskRegister implements DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(CustomCronTaskRegister.class);


    @Resource(name = "dynamic-schedule-taskScheduler")
    private TaskScheduler taskScheduler;

    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }


    public void addCronTask(DcSchedulingRunnable task, String cronExpression) {
        String taskId = task.getTaskId();
        if (null != ConstantsPool.TASK_CONCURRENT_HASH_MAP.get(taskId)) {
            removeCronTask(taskId);
        }
        CronTask cronTask = new CronTask(task, cronExpression);
        ConstantsPool.TASK_CONCURRENT_HASH_MAP.put(taskId, scheduleCronTask(cronTask));
        logger.info("添加定时任务成功，定时任务的cron表达式:{}, taskId:{}",cronExpression,taskId);
    }

    /**
     * 新增初始化添加定时任务的方法
     * @param task
     */
    public void initCronTask(DcSchedulingRunnable task) {
        ConstantsPool.SCHEDULING_RUNNABLE_MAP.put(task.getTaskId(),task);
        logger.info("初始化添加定时任务的cron表达式:{}, taskId:{}",task.getCronExpression(),task.getTaskId());
    }

    public void removeCronTask(String taskId) {
        ScheduledTask scheduledTask = ConstantsPool.TASK_CONCURRENT_HASH_MAP.remove(taskId);
        if (scheduledTask == null) return;
        scheduledTask.cancel();
        logger.info("取消定时任务成功, taskId :{}",taskId);
        //取消成功就把状态改成false
        DcSchedulingRunnable dcSchedulingRunnable = ConstantsPool.SCHEDULING_RUNNABLE_MAP.get(taskId);
        dcSchedulingRunnable.setStatus(false);
    }

    private ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    @Override
    public void destroy() {
        for (ScheduledTask task : ConstantsPool.TASK_CONCURRENT_HASH_MAP.values()) {
            task.cancel();
        }
        ConstantsPool.TASK_CONCURRENT_HASH_MAP.clear();
        ConstantsPool.SCHEDULING_RUNNABLE_MAP.clear();
    }
}
