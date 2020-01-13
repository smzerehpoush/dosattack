package com.mahdiyar.dosattack.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RedisHash
@NoArgsConstructor
@Data
public class UserTokenEntity {
    private String id;
    private String hashedToken;
    private long creationTime;

    public UserTokenEntity(String id, String hashedToken) {
        this.id = id;
        this.hashedToken = hashedToken;
        this.creationTime = System.currentTimeMillis();
    }
}
