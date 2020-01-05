package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RequestMapping("/api/v1/test")
@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping
    public String test() {
        return testService.test();
    }

    @GetMapping(value = "/current")
    public ResponseEntity<String> currentValue() {
        return ResponseEntity.ok(testService.currentValue());
    }
}
