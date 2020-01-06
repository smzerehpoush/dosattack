package com.mahdiyar.dosattack.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.UUID;

/**
 * @author mahdiyar
 */
@Getter
@Setter
@Document("request_log")
@NoArgsConstructor
public class RequestLogEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String ip;
    private String method;
    private Date creationDate;

    public RequestLogEntity(String ip, String method, Date creationDate) {
        this.ip = ip;
        this.method = method;
        this.creationDate = creationDate;
    }
}
