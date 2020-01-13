package com.mahdiyar.dosattack.exceptions;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }
}
