package com.mahdiyar.dosattack.exceptions.handler;

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
