package com.dc.dynamic.schedule.config.event;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

public class DynamicScheduleService {

    @Autowired
    private EventBus eventBus;

    /**
     * 新增动态更新定时任务方法
     * @param dynamicScheduleEvent
     */
    public void updateSchedule(DynamicScheduleEvent dynamicScheduleEvent) {
        eventBus.post(dynamicScheduleEvent);
    }
}
