package com.mahdiyar.dosattack.model.dto.request.loan;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@AllArgsConstructor
public class LoanRequestDto {
    private String userId;
    private String bankId;
    private Long amount;
}
