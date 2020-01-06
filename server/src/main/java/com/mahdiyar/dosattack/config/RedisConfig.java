package com.mahdiyar.dosattack.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "database.redis", ignoreUnknownFields = false)
public class RedisConfig {
    @Setter
    private String hostName;
    @Setter
    private int port;
    @Setter
    private String password;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);
        redisStandaloneConfiguration.setPassword(password.toCharArray());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
}
