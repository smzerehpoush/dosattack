package com.mahdiyar.dosattack.model.dto.bank;

import com.mahdiyar.dosattack.model.entity.BankEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class BankDto {
    private String id;
    private String name;
    private Long balance;

    public BankDto(BankEntity bankEntity) {
        this.id = bankEntity.getId();
        this.name = bankEntity.getName();
        this.balance = bankEntity.getBalance();
    }
}
