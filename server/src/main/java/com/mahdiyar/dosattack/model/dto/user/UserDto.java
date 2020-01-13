package com.mahdiyar.dosattack.model.dto.user;

import com.mahdiyar.dosattack.model.entity.mysql.UserEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class UserDto extends BriefUserDto {
    public UserDto(UserEntity userEntity) {
        super(userEntity);
    }
}
