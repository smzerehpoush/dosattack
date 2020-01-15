package com.mahdiyar.dosattack.model.dto.request.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
}
