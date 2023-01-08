package com.dc.dynamic.schedule.common;


import com.dc.dynamic.schedule.task.DcSchedulingRunnable;
import com.dc.dynamic.schedule.task.ScheduledTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 *  常量池工具类
 */
public class ConstantsPool {

    /**
     *  key taskId , value Task
     */
    public static final Map<String, ScheduledTask> TASK_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>(16);

    /**
     * key taskId value dcSchedulingRunnable
     */
    public static final Map<String, DcSchedulingRunnable> SCHEDULING_RUNNABLE_MAP = new ConcurrentHashMap<>(16);
}
