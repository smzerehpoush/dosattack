package com.mahdiyar.dosattack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
public class MessageService {
    @Autowired
    private MessageSource messageSource;
    private static final Locale defaultLocalce = new Locale("en", "US");

    public String getMessage(String messageKey) {
        return getMessage(messageKey, null);
    }

    public String getMessage(String messageKey, Map<String, Object> params) {
        String message = messageSource.getMessage(messageKey, null, defaultLocalce);
        if (params == null)
            return message;
        for (String key : params.keySet()) {
            message = message.replace("{" + key + "}", String.valueOf(params.get(key)));
        }
        return message;
    }
}
