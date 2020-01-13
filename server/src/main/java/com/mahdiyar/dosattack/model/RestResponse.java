package com.mahdiyar.dosattack.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class RestResponse<T> {
    private int code;
    private String message;
    private T content;
    private Date responseTime;

    public RestResponse() {
        this.responseTime = new Date();
    }

    public static <T> RestResponse<T> ok(T content) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = 0;
        restResponse.message = "OK";
        restResponse.content = content;
        return restResponse;
    }

    public static <T> RestResponse<T> nok(T content, Integer code, String message) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = code;
        restResponse.message = message;
        restResponse.content = content;
        return restResponse;
    }

    public static RestResponse<Void> nok(Integer code, String message) {
        RestResponse<Void> restResponse = new RestResponse<>();
        restResponse.code = code;
        restResponse.message = message;
        restResponse.content = null;
        return restResponse;
    }
}
