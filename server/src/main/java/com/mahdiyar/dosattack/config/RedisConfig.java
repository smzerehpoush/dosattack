package com.mahdiyar.dosattack.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Configuration
@ConfigurationProperties(prefix = "database.redis")
@EnableRedisRepositories(basePackages = "com.mahdiyar.dosattack.repository.redisRepositories")
public class RedisConfig {
    @Setter
    private String host;
    @Setter
    private int port;
    @Setter
    private String password;

    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(redisStandaloneConfiguration());
    }

    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(password.toCharArray());
        return redisStandaloneConfiguration;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        return template;
    }
}
