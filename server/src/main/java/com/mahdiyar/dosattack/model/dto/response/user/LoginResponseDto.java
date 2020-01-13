package com.mahdiyar.dosattack.model.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class LoginResponseDto {
    private String id;
    private String username;

    public LoginResponseDto(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
