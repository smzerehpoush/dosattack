package com.mahdiyar.dosattack.model.dto.user;

import com.mahdiyar.dosattack.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class UserDto extends BriefUserDto {
    public UserDto(UserEntity userEntity) {
        super(userEntity);
    }
}
