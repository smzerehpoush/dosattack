package com.mahdiyar.dosattack.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Configuration
public class ThreadConfig {

    @Setter
    @Value("${com.mahdiyar.os-project.configuration.thread-config.max-pool-size}")
    private int maxPoolSize;
    @Setter
    @Value("${com.mahdiyar.os-project.configuration.thread-config.core-pool-size}")
    private int corePoolSize;

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
        return executor;
    }
}
