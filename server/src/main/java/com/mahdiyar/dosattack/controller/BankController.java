package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.exceptions.GeneralExistsException;
import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.bank.BankDto;
import com.mahdiyar.dosattack.model.dto.bank.CreateBankRequestDto;
import com.mahdiyar.dosattack.security.AuthRequired;
import com.mahdiyar.dosattack.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/v1/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping
    public RestResponse<List<BankDto>> getBanks() {
        return RestResponse.ok(bankService.getBanks());
    }

    @GetMapping("/{bankId}")
    public RestResponse<BankDto> getBank(@PathVariable("bankId") String bankId) throws GeneralNotFoundException {
        return RestResponse.ok(bankService.getBank(bankId));
    }

    @PostMapping
    public RestResponse<BankDto> createBank(@RequestBody CreateBankRequestDto requestDto) throws GeneralExistsException {
        return RestResponse.ok(bankService.createBank(requestDto));
    }

    @AuthRequired(admin = true)
    @PutMapping("/{bankId}")
    public RestResponse<BankDto> updateBankBalance(@PathVariable("bankId") String bankId, @RequestParam("balance") Long balance) throws GeneralNotFoundException {
        return RestResponse.ok(bankService.updateBankBalance(bankId, balance));
    }
}
