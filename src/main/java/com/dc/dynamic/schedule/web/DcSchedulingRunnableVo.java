package com.dc.dynamic.schedule.web;

/**
 *  动态定时任务视图出参类
 */
public class DcSchedulingRunnableVo {

    private String desc;

    private String cronExpression;

    private String taskId;

    private Boolean status = true;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public DcSchedulingRunnableVo() {
    }

    public DcSchedulingRunnableVo(String desc, String cronExpression, String taskId, Boolean status) {
        this.desc = desc;
        this.cronExpression = cronExpression;
        this.taskId = taskId;
        this.status = status;
    }
}
