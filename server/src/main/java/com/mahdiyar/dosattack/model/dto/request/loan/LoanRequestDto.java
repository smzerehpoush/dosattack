package com.mahdiyar.dosattack.model.dto.request.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDto {
    private String userId;
    private String bankId;
    private Long amount;
}
