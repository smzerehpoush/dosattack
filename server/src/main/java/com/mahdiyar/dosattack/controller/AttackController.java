package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.service.AttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RequestMapping("api/v1/attack")
@RestController
public class AttackController {
    @Autowired
    private AttackService attackService;

    @GetMapping
    public ResponseEntity<String> attack(@RequestParam("init") int init, @RequestParam("max") int max, @RequestParam("steps") int steps) {
        return ResponseEntity.ok(attackService.attack(init, max, steps));
    }

    @GetMapping("/statistics")
    public ResponseEntity<String> statistics() {
        return ResponseEntity.ok(attackService.statistics());
    }
}