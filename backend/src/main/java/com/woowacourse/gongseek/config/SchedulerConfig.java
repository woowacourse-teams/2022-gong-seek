package com.woowacourse.gongseek.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

@RequiredArgsConstructor
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    private final List<TriggerTask> tasks;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (TriggerTask task : tasks) {
            taskRegistrar.addTriggerTask(task);
        }
    }
}
