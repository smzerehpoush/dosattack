package com.mahdiyar.dosattack.model.dto.bank;

import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class CreateBankRequestDto {
    private String name;
    private Long balance;
}
