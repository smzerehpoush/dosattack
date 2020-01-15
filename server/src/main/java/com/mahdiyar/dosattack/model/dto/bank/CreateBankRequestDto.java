package com.mahdiyar.dosattack.model.dto.bank;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class CreateBankRequestDto {
    private String name;
    private Long balance;
}
