package com.mahdiyar.dosattack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@Slf4j
public class TestService {
    public static final int INITIAL_AMOUNT = 100000;
    public static int AMOUNT = INITIAL_AMOUNT;

    public String test() {
        int temp = AMOUNT;
        Random rnd = new Random(System.currentTimeMillis());
        AMOUNT = rnd.nextInt();
        AMOUNT = (int) Math.log(AMOUNT);
        AMOUNT = temp;
//        log.info("amount : {}", AMOUNT);
        return String.valueOf(--AMOUNT);
    }

    public String currentValue() {
        return String.valueOf(AMOUNT);
    }
}
