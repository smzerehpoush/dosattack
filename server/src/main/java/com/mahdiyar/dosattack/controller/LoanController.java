package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.common.RequestContext;
import com.mahdiyar.dosattack.exceptions.CanNotSeeOthersLoanException;
import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.loan.LoanDto;
import com.mahdiyar.dosattack.model.dto.request.loan.LoanRequestDto;
import com.mahdiyar.dosattack.security.AuthRequired;
import com.mahdiyar.dosattack.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Api("Loan Controller")
@RestController
@RequestMapping("/v1/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private RequestContext requestContext;

    @ApiOperation(value = "Get List Of Loans Details", notes = "Admin Auth Required.")
    @GetMapping
    @AuthRequired(admin = true)
    public RestResponse<List<LoanDto>> getLoans() {
        return RestResponse.ok(loanService.getLoans());
    }

    @ApiOperation(value = "Get List Of Loans Details", notes = "Admin Auth Required.")
    @AuthRequired
    @GetMapping("/{loanId}")
    public RestResponse<LoanDto> getLoan(@PathVariable("loanId") String loanId) throws GeneralNotFoundException, CanNotSeeOthersLoanException {
        return RestResponse.ok(loanService.getLoan(loanId, requestContext.getUser()));
    }

    @ApiOperation(value = "Request New Loan", notes = "User Auth Required.")
    @AuthRequired
    @PostMapping
    public RestResponse<LoanDto> requestNewLoad(@RequestBody LoanRequestDto requestDto) throws GeneralNotFoundException {
        return RestResponse.ok(loanService.requestNewLoad(requestDto));
    }
}
