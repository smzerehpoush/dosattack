package com.mahdiyar.dosattack.model.dto.user;

import com.mahdiyar.dosattack.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class BriefUserDto {
    private String id;
    private String username;
    private long balance;

    public BriefUserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.balance = userEntity.getBalance();
    }
}
