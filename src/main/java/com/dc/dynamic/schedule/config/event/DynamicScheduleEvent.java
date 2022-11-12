package com.dc.dynamic.schedule.config.event;

/**
 * DynamicSchedule事件驱动模型
 */
public class DynamicScheduleEvent {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 定时任务表达式
     */
    private String  cronExpression;

    /**
     * 是否取消，默认false
     */
    private Boolean  cancel = false;

    public DynamicScheduleEvent(String taskId, String cronExpression, Boolean cancel) {
        this.taskId = taskId;
        this.cronExpression = cronExpression;
        this.cancel = cancel;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public DynamicScheduleEvent() {
    }
}
