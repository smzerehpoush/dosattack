package com.mahdiyar.dosattack.exceptions.handler;

import com.mahdiyar.dosattack.exceptions.ServiceException;
import com.mahdiyar.dosattack.exceptions.UserNotAuthenticatedException;
import com.mahdiyar.dosattack.exceptions.UsernameOrPasswordIncorrectException;
import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.service.MessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {
    private static final String ERROR_CODE_BASE = "err.";
    private static final Integer DEFAULT_ERROR_CODE = -1;
    @Autowired
    private MessageService messageService;

    @ExceptionHandler({UsernameOrPasswordIncorrectException.class, UserNotAuthenticatedException.class})
    public ResponseEntity<RestResponse<Void>> handleUsernameOrPasswordIncorrectException(ServiceException ex) {
        ExceptionDetai exceptionDetail = findExceptionDetail(ex);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(RestResponse.nok(exceptionDetail.getCode(), exceptionDetail.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Void>> handleException(ServiceException ex) {
        ExceptionDetai exceptionDetail = findExceptionDetail(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.nok(exceptionDetail.getCode(), exceptionDetail.getMessage()));
    }

    private ExceptionDetai findExceptionDetail(Exception ex) {
        if (ex.getClass().isAnnotationPresent(HandledException.class)) {
            Map<String, Object> paramsMap;
            paramsMap = new HashMap<>(ex.getClass().getDeclaredFields().length);
            for (Field declaredField : ex.getClass().getDeclaredFields()) {
                try {
                    paramsMap.put(declaredField.getName(), ex.getClass().getDeclaredMethod("get" + StringUtils.capitalize(declaredField.getName())).invoke(ex));
                } catch (Exception e) {
                    logger.error("can not get field value {}", declaredField.getName());
                }
            }
            HandledException handledException = ex.getClass().getAnnotation(HandledException.class);
            String messageKey = handledException.value().isEmpty() ? handledException.message() : handledException.value();
            String message = messageService.getMessage(messageKey, paramsMap);
            Integer code = findExceptionCode(messageKey);
            return new ExceptionDetai(code, message);
        } else {
            for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
                if (cause instanceof Exception && cause.getClass().isAnnotationPresent(HandledException.class)) {
                    return findExceptionDetail((Exception) cause);
                }
            }
            return new ExceptionDetai(DEFAULT_ERROR_CODE, messageService.getMessage(ERROR_CODE_BASE + DEFAULT_ERROR_CODE));
        }
    }

    private Integer findExceptionCode(String codeStr) {
        if (!codeStr.startsWith(ERROR_CODE_BASE))
            return -1;
        else return Integer.parseInt(codeStr.substring(ERROR_CODE_BASE.length()));
    }

    @Data
    private class ExceptionDetai {
        Integer code;
        String message;

        public ExceptionDetai(Integer code, String message) {
            this.code = code;
            this.message = message;
        }


    }
}
