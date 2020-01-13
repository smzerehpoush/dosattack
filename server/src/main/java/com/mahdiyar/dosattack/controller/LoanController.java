package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.loan.LoanDto;
import com.mahdiyar.dosattack.model.dto.request.loan.LoanRequestDto;
import com.mahdiyar.dosattack.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    public RestResponse<LoanDto> requestNewLoad(@RequestBody LoanRequestDto requestDto) {
        return RestResponse.ok(loanService.requestNewLoad(requestDto));
    }
}
