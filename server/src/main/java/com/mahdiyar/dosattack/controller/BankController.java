package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.exceptions.GeneralExistsException;
import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.bank.BankDto;
import com.mahdiyar.dosattack.model.dto.bank.CreateBankRequestDto;
import com.mahdiyar.dosattack.security.AuthRequired;
import com.mahdiyar.dosattack.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Api("Bank Controller")
@RestController
@RequestMapping("/v1/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    @ApiOperation(value = "Get List Of Banks Details", notes = "No Auth Required.")
    @GetMapping
    public RestResponse<List<BankDto>> getBanks() {
        return RestResponse.ok(bankService.getBanks());
    }

    @ApiOperation(value = "Get Bank Detail By Id", notes = "No Auth Required.")
    @GetMapping("/{bankId}")
    public RestResponse<BankDto> getBank(@PathVariable("bankId") String bankId) throws GeneralNotFoundException {
        return RestResponse.ok(bankService.getBank(bankId));
    }

    @ApiOperation(value = "Create Bank", notes = "Admin Auth Required.")
    @PostMapping
    public RestResponse<BankDto> createBank(@RequestBody CreateBankRequestDto requestDto) throws GeneralExistsException {
        return RestResponse.ok(bankService.createBank(requestDto));
    }

    @ApiOperation(value = "Create Bank", notes = "Admin Auth Required.")
    @AuthRequired(admin = true)
    @PutMapping("/{bankId}")
    public RestResponse<BankDto> updateBankBalance(@PathVariable("bankId") String bankId, @RequestParam("balance") Long balance) throws GeneralNotFoundException {
        return RestResponse.ok(bankService.updateBankBalance(bankId, balance));
    }
}
