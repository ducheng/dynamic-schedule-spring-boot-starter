package com.dc.dynamic.schedule.config;

import com.dc.dynamic.schedule.common.ConstantsPool;
import com.dc.dynamic.schedule.task.CustomCronTaskRegister;
import com.dc.dynamic.schedule.task.DcSchedulingRunnable;
import com.dc.dynamic.schedule.web.DcSchedulingRunnableDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;


public class DynamicScheduleService {

    private static Logger logger = LoggerFactory.getLogger(DynamicScheduleService.class);

    @Autowired
    private CustomCronTaskRegister customCronTaskRegister;


    /**
     * 启动task 任务
     * @param taskId
     */
    public void startScheduleTask(String taskId) {
        DcSchedulingRunnable dcSchedulingRunnable = ConstantsPool.SCHEDULING_RUNNABLE_MAP.get(taskId);
        if (ObjectUtils.isEmpty(dcSchedulingRunnable)) {
            logger.info("taskId 不存在，请重试");
        } else {
            dcSchedulingRunnable.setStatus(true);
            customCronTaskRegister.addCronTask(dcSchedulingRunnable,dcSchedulingRunnable.getCronExpression());
        }
    }

    /**
     * 取消task 任务
     * @param taskId
     */
    public void removeCronTask(String taskId) {
        DcSchedulingRunnable dcSchedulingRunnable = ConstantsPool.SCHEDULING_RUNNABLE_MAP.get(taskId);
        if (ObjectUtils.isEmpty(dcSchedulingRunnable)) {
            logger.info("taskId 不存在，请重试");
        } else {
            dcSchedulingRunnable.setStatus(false);
            customCronTaskRegister.removeCronTask(taskId);
        }
    }

    /**
     *  更新task 任务
     * @param dcSchedulingRunnableDto
     */
    public void updateSchedule(DcSchedulingRunnableDto dcSchedulingRunnableDto) {
        DcSchedulingRunnable dcSchedulingRunnable = ConstantsPool.SCHEDULING_RUNNABLE_MAP.get(dcSchedulingRunnableDto.getTaskId());
        if (ObjectUtils.isEmpty(dcSchedulingRunnable)) {
            logger.info("taskId 不存在，请重试");
        } else {
            dcSchedulingRunnable.setStatus(true);
            dcSchedulingRunnable.setCronExpression(dcSchedulingRunnableDto.getCronExpression());
            customCronTaskRegister.addCronTask(dcSchedulingRunnable,dcSchedulingRunnable.getCronExpression());
        }
    }
}
