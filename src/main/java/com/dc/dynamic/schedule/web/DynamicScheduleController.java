package com.dc.dynamic.schedule.web;


import com.dc.dynamic.schedule.common.ConstantsPool;
import com.dc.dynamic.schedule.config.DynamicScheduleService;
import com.dc.dynamic.schedule.task.DcSchedulingRunnable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  动态定时任务的视图层
 */
@Controller
@RequestMapping("/dynamicSchedule")
public class DynamicScheduleController {

    @Autowired
    private DynamicScheduleService dynamicScheduleService;


    /**
     * 查询所有动态定时任务列表
     * @return
     */
    @GetMapping("/index")
    public String  index(Model model) {
        List<DcSchedulingRunnable> runnableList = ConstantsPool.SCHEDULING_RUNNABLE_MAP.values().stream().collect(Collectors.toList());
        List<DcSchedulingRunnableVo> runnableVos = new ArrayList<>();
        runnableList.stream().forEach(x-> {
            DcSchedulingRunnableVo runnableVo = new DcSchedulingRunnableVo();
            BeanUtils.copyProperties(x,runnableVo);
            runnableVos.add(runnableVo);
        });
        model.addAttribute("list", runnableList);
        return "index";
    }

    /**
     * 动态修改定时任务版本
     */
    @PostMapping("/updateSchedule")
    public String updateSchedule(DcSchedulingRunnableDto dcSchedulingRunnableDto,RedirectAttributes attributes ) {
        dynamicScheduleService.updateSchedule(dcSchedulingRunnableDto);
        attributes.addFlashAttribute("message", "修改动态定时任务成功");
        return "redirect:/dynamicSchedule/index";
    }



    @GetMapping("/edit/{taskId}")
    public String toEdit(@PathVariable("taskId")String taskId, Model model) {
        List<DcSchedulingRunnable> runnableList = ConstantsPool.SCHEDULING_RUNNABLE_MAP.values().stream().collect(Collectors.toList());
        DcSchedulingRunnable dcSchedulingRunnable = runnableList.stream().filter(x -> x.getTaskId().equals(taskId)).findFirst().get();
        model.addAttribute("job", dcSchedulingRunnable);
        return "editJob";
    }


    /**
     * 开启动态定时任务
     */
    @GetMapping("/startScheduleTask/{taskId}")
    public String startScheduleTask(@PathVariable("taskId")String taskId,RedirectAttributes attributes) {
        dynamicScheduleService.startScheduleTask(taskId);
        attributes.addFlashAttribute("message", "开启动态定时任务成功");
        return "redirect:/dynamicSchedule/index";
    }

    /**
     * 取消动态定时任务
     */
    @GetMapping("/removeCronTask/{taskId}")
    public String removeCronTask(@PathVariable("taskId")String taskId,RedirectAttributes attributes) {
        dynamicScheduleService.removeCronTask(taskId);
        attributes.addFlashAttribute("message", "取消动态定时任务成功");
        return "redirect:/dynamicSchedule/index";
    }

    /**
     * 手动执行一次
     */
    @GetMapping("/startOne/{taskId}")
    public String startOneSchedule(@PathVariable("taskId") String taskId, RedirectAttributes attributes) {
        DcSchedulingRunnable dcSchedulingRunnable = ConstantsPool.SCHEDULING_RUNNABLE_MAP.get(taskId);
        Thread thread = new Thread(dcSchedulingRunnable);
        thread.start();
        attributes.addFlashAttribute("message", "手动执行一次成功");
        return "redirect:/dynamicSchedule/index";
    }
}
