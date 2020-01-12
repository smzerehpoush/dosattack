package com.mahdiyar.dosattack.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@EnableMongoRepositories(basePackages = {"com.mahdiyar.dosattack.repository.*"})
@Configuration
@ConfigurationProperties(prefix = "database.mongo", ignoreUnknownFields = false)
public class MongoConfig {
    @Setter
    private String hostName;
    @Setter
    private Integer port;
    @Setter
    private String database;
    @Setter
    private String username;
    @Setter
    private String password;

    @Setter
    private Integer threadsAllowedToBlockForConnectionMultiplier;
    @Setter
    private Integer connectTimeout;
    @Setter
    private Integer maxConnectionIdleTime;
    @Setter
    private Integer maxWaitTime;
    @Setter
    private Integer connectionsPerHost;

    @Bean
//    @Primary
    public MongoClient mongoClient() {
        MongoClientOptions options = MongoClientOptions.builder()
                .threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier)
                .connectTimeout(connectTimeout)
                .maxConnectionIdleTime(maxConnectionIdleTime)
                .maxWaitTime(maxWaitTime)
                .connectionsPerHost(connectionsPerHost)
                .build();
        ServerAddress serverAddress = new ServerAddress(hostName, port);
        MongoCredential mongoCredential = MongoCredential.createCredential(username, database, password.toCharArray());
        return new MongoClient(serverAddress, mongoCredential, options);
    }
}
