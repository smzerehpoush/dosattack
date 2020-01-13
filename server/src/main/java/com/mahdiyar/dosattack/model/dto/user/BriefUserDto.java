package com.mahdiyar.dosattack.model.dto.user;

import com.mahdiyar.dosattack.model.entity.mysql.UserEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class BriefUserDto {
    private String id;
    private String username;
    private int balance;

    public BriefUserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.balance = userEntity.getBalance();
    }
}
