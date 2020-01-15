package com.mahdiyar.dosattack.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "com.mahdiyar.dosattack.configuration.thread", ignoreUnknownFields = false)
public class TaskExecutorConfig {
    @Setter
    private int corePoolSize;
    @Setter
    private int maxPoolSize;
    @Setter
    private int queueCapacity;

    @Bean
    @Primary
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
        return executor;
    }
}
