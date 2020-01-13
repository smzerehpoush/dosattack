package com.mahdiyar.dosattack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.mahdiyar.dosattack.repository.mysqlRepositories"})
public class MySQLConfig {
}
