package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.service.AttackService;
import com.mahdiyar.dosattack.service.BankAttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RequestMapping("/v1/attack")
@RestController
public class AttackController {
    @Autowired
    private AttackService attackService;
    @Autowired
    private BankAttackService bankAttackService;

    @GetMapping
    public ResponseEntity<String> attack(
            @RequestParam(name = "init", defaultValue = "10") int init,
            @RequestParam(name = "max", defaultValue = "100") int max,
            @RequestParam(name = "steps", defaultValue = "5") int steps) throws InterruptedException {
        return ResponseEntity.ok(attackService.attack(init, max, steps));
    }

    @GetMapping("/bank")
    public ResponseEntity bankAttack(
            @RequestParam(name = "size", defaultValue = "100") int size) throws InterruptedException {
        return ResponseEntity.ok(bankAttackService.bankAttack(size));
    }

    @GetMapping("/statistics")
    public ResponseEntity<String> statistics() {
        return ResponseEntity.ok(attackService.statistics());
    }
}
