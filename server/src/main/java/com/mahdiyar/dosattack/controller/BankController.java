package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.service.BankService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/bank")
public class BankController {
    private BankService bankService;
}
