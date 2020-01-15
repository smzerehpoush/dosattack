package com.mahdiyar.dosattack.model.dto.loan;

import com.mahdiyar.dosattack.model.dto.bank.BankDto;
import com.mahdiyar.dosattack.model.dto.user.BriefUserDto;
import com.mahdiyar.dosattack.model.entity.LoanEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class LoanDto {
    private BriefUserDto user;
    private BankDto bank;
    private Long amount;
    private Date creationDate;
    private boolean done;

    public LoanDto(LoanEntity loanEntity) {
        this.user = new BriefUserDto(loanEntity.getUser());
        this.bank = new BankDto(loanEntity.getBank());
        this.amount = loanEntity.getAmount();
        this.creationDate = loanEntity.getCreationDate();
        this.done = loanEntity.isDone();
    }
}
