package com.dc.dynamic.schedule.web;

/**
 *  动态定时任务视图入参类
 */
public class DcSchedulingRunnableDto {

    private String cronExpression;

    private String taskId;

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
