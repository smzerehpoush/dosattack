package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RequestMapping("/v1/verify")
@RestController
public class VerifyController {
    @Autowired
    private VerifyService verifyService;

    @GetMapping
    public ResponseEntity<String> verify() throws InterruptedException {
        return ResponseEntity.ok(verifyService.verify());
    }
}
