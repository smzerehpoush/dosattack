package com.mahdiyar.dosattack.exceptions;

import com.mahdiyar.dosattack.exceptions.handler.HandledException;
import lombok.Getter;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@HandledException("err.100")
@Getter
public class UserWithUsernameExistsException extends ServiceException {
    private String username;

    public UserWithUsernameExistsException(String username) {
        this.username = username;
    }
}
