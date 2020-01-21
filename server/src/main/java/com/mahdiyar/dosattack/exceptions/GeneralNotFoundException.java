package com.mahdiyar.dosattack.exceptions;

import com.mahdiyar.dosattack.exceptions.handler.HandledException;
import com.mahdiyar.dosattack.exceptions.handler.ServiceException;
import lombok.Getter;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@HandledException("err.101")
@Getter
public class GeneralNotFoundException extends ServiceException {
    private String item;
    private String key;
    private String value;

    public GeneralNotFoundException(String item, String key, String value) {
        super(item + " with " + key + " : " + value + " not found.");
        this.item = item;
        this.key = key;
        this.value = value;
    }
}
