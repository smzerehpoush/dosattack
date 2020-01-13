package com.mahdiyar.dosattack.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RedisHash("user")
@NoArgsConstructor
@Data
@Entity
public class UserTokenEntity implements Serializable {
    @Id
    private String id;//saving hashed token in this field
    private String userId;
    private long creationTime;

    public UserTokenEntity(String userId, String hashedToken) {
        this.userId = userId;
        this.id = hashedToken;
        this.creationTime = System.currentTimeMillis();
    }
}
