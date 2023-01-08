package com.dc.dynamic.schedule.web;


import com.dc.dynamic.schedule.common.ConstantsPool;
import com.dc.dynamic.schedule.config.DynamicScheduleService;
import com.dc.dynamic.schedule.task.DcSchedulingRunnable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  动态定时任务的视图层
 */
@RestController
@RequestMapping("/dynamicSchedule")
public class DynamicScheduleController {

    @Autowired
    private DynamicScheduleService dynamicScheduleService;


    /**
     * 查询所有动态定时任务列表
     * @return
     */
    @GetMapping("/getList")
    public List<DcSchedulingRunnableVo> getList() {
        List<DcSchedulingRunnable> runnableList = ConstantsPool.SCHEDULING_RUNNABLE_MAP.values().stream().collect(Collectors.toList());
        List<DcSchedulingRunnableVo> runnableVos = new ArrayList<>();
        runnableList.stream().forEach(x-> {
            DcSchedulingRunnableVo runnableVo = new DcSchedulingRunnableVo();
            BeanUtils.copyProperties(x,runnableVo);
            runnableVos.add(runnableVo);
        });
        return runnableVos;
    }

    /**
     * 动态修改定时任务版本
     */
    @PostMapping("/updateSchedule")
    public String updateSchedule(@RequestBody DcSchedulingRunnableDto dcSchedulingRunnableDto ) {
        dynamicScheduleService.updateSchedule(dcSchedulingRunnableDto);
        return "ok";
    }


    /**
     * 开启动态定时任务
     */
    @GetMapping("/startScheduleTask/{taskId}")
    public String startScheduleTask(@PathVariable("taskId")String taskId ) {
        dynamicScheduleService.startScheduleTask(taskId);
        return "ok";
    }

    /**
     * 取消动态定时任务
     */
    @GetMapping("/removeCronTask/{taskId}")
    public String removeCronTask(@PathVariable("taskId")String taskId ) {
        dynamicScheduleService.removeCronTask(taskId);
        return "ok";
    }

    /**
     * 手动执行一次
     */
    @GetMapping("/startOnce/{taskId}")
    public String updateSchedule(@PathVariable("taskId") String taskId) {
        DcSchedulingRunnable dcSchedulingRunnable = ConstantsPool.SCHEDULING_RUNNABLE_MAP.get(taskId);
        Thread thread = new Thread(dcSchedulingRunnable);
        thread.start();
        return "ok";
    }
}
