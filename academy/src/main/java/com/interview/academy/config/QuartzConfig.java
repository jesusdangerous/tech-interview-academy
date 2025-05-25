package com.interview.academy.config;

import com.interview.academy.jobs.UserReminderJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail userReminderJobDetail() {
        return JobBuilder.newJob(UserReminderJob.class)
                .withIdentity("userReminderJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger userReminderTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(userReminderJobDetail())
                .withIdentity("userReminderTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(48)
                        .repeatForever())
                .build();
    }
}
