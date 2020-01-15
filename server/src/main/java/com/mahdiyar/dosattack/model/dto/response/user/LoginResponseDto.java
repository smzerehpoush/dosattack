package com.mahdiyar.dosattack.model.dto.response.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class LoginResponseDto {
    private String id;
    private String username;

    public LoginResponseDto(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
