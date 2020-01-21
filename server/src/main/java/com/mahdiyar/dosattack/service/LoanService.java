package com.mahdiyar.dosattack.service;

import com.google.common.collect.ImmutableList;
import com.mahdiyar.dosattack.exceptions.CanNotSeeOthersLoanException;
import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.exceptions.UserNotAuthenticatedException;
import com.mahdiyar.dosattack.model.dto.loan.LoanDto;
import com.mahdiyar.dosattack.model.dto.request.loan.LoanRequestDto;
import com.mahdiyar.dosattack.model.entity.BankEntity;
import com.mahdiyar.dosattack.model.entity.LoanEntity;
import com.mahdiyar.dosattack.model.entity.UserEntity;
import com.mahdiyar.dosattack.repository.mysqlRepositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BankService bankService;

    public LoanDto requestNewLoad(LoanRequestDto requestDto) throws GeneralNotFoundException {
        UserEntity userEntity = userService.findById(requestDto.getUserId());
        BankEntity bankEntity = bankService.findById(requestDto.getBankId());
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setUser(userEntity);
        loanEntity.setBank(bankEntity);
        loanEntity.setAmount(requestDto.getAmount());
        loanRepository.save(loanEntity);
        if (bankEntity.getBalance() > (loanEntity.getAmount() * 1.1)) {
            bankService.decreaseLoanAmount(bankEntity, loanEntity.getAmount());
            userService.increaseLoanAmount(userEntity, loanEntity.getAmount());
            loanEntity.setDone(true);
        } else {
            loanEntity.setDone(false);
        }
        loanRepository.save(loanEntity);
        return new LoanDto(loanEntity);
    }

    public LoanEntity findById(String loanId) throws GeneralNotFoundException {
        Optional<LoanEntity> optionalLoanEntity = loanRepository.findById(loanId);
        if (!optionalLoanEntity.isPresent())
            throw new GeneralNotFoundException("Loan", "Id", loanId);
        return optionalLoanEntity.get();
    }

    public List<LoanDto> getLoans() {
        List<LoanEntity> loanEntities = loanRepository.findAll();
        List<LoanDto> loanDtos = ImmutableList.of();
        if (!loanEntities.isEmpty()) {
            loanDtos = loanEntities.stream().map(LoanDto::new).collect(Collectors.toList());
        }
        return loanDtos;
    }

    public LoanDto getLoan(String loanId, UserEntity user) throws GeneralNotFoundException, CanNotSeeOthersLoanException {
        LoanEntity loanEntity = findById(loanId);
        if (!loanEntity.getUser().equals(user) && !loanEntity.getUser().isAdmin())
            throw new CanNotSeeOthersLoanException();
        return new LoanDto(findById(loanId));
    }
}
