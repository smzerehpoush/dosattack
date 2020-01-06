package com.mahdiyar.dosattack.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "com.mahdiyar.dosattack.configuration.rest-template", ignoreUnknownFields = false)
public class RestTemplateConfig {
    @Setter
    private int connectionTimeout;
    @Setter
    private int readTimeout;


    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
