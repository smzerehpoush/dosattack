package com.mahdiyar.dosattack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@Slf4j
public class VerifyService {
    private static int count = 0;

    public String verify() throws InterruptedException {
        if (count % 6 == 1)
            Thread.sleep(100);
        if (count % 6 == 2)
            Thread.sleep(80);
        if (count % 6 == 3)
            Thread.sleep(10);
        if (count % 6 == 4)
            Thread.sleep(0);
        if (count % 6 == 5)
            Thread.sleep(50);
        count++;
        logger.info("{}", count);
        return String.valueOf(count);
    }
}
