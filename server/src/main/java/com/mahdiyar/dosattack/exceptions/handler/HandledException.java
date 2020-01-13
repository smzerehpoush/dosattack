package com.mahdiyar.dosattack.exceptions.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandledException {
    String value();

    String message() default "err.-1";
}
