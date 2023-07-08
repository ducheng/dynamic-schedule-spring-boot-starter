package com.dc.dynamic.schedule;


import com.dc.dynamic.schedule.annotation.DynamicScheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTest {

    @DynamicScheduled(desc = "测试",cron = "${cron.test}" )
    public void test(){
        System.out.printf("当前时间"+new Date());
    }

}
