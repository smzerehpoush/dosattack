package com.mahdiyar.dosattack.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class RestResponse<T> {
    private int code;
    private String message;
    private T content;
    private Long responseTime;

    public static <T> RestResponse<T> ok(T content) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = 0;
        restResponse.message = "OK";
        restResponse.content = content;
        restResponse.setResponseTime(System.currentTimeMillis());
        return restResponse;
    }

    public static <T> RestResponse<T> nok(T content, Integer code, String message) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = code;
        restResponse.message = message;
        restResponse.content = content;
        restResponse.responseTime = System.currentTimeMillis();
        return restResponse;
    }

    public static RestResponse<Void> nok(Integer code, String message) {
        return nok(null, code, message);
    }
}
