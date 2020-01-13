package com.mahdiyar.dosattack.exceptions;

import com.mahdiyar.dosattack.exceptions.handler.HandledException;
import lombok.Getter;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@HandledException("err.104")
@Getter
public class GeneralExistsException extends ServiceException {
    private String item;
    private String key;
    private String value;

    public GeneralExistsException(String item, String key, String value) {
        super(item + " with " + key + " : " + value + " exists.");
        this.item = item;
        this.key = key;
        this.value = value;
    }
}
