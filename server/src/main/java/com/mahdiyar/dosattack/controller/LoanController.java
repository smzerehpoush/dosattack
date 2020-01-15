package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.loan.LoanDto;
import com.mahdiyar.dosattack.model.dto.request.loan.LoanRequestDto;
import com.mahdiyar.dosattack.security.AuthRequired;
import com.mahdiyar.dosattack.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/v1/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    @AuthRequired(admin = true)
    public RestResponse<List<LoanDto>> getLoans() {
        return RestResponse.ok(loanService.getLoans());
    }

    @AuthRequired(admin = true)
    @GetMapping("/{loanId}")
    public RestResponse<LoanDto> getLoan(@PathVariable("loanId") String loanId) throws GeneralNotFoundException {
        return RestResponse.ok(loanService.getLoan(loanId));
    }

    @AuthRequired
    @PostMapping
    public RestResponse<LoanDto> requestNewLoad(@RequestBody LoanRequestDto requestDto) throws GeneralNotFoundException {
        return RestResponse.ok(loanService.requestNewLoad(requestDto));
    }
}
