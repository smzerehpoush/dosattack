package com.mahdiyar.dosattack.service;

import com.google.common.collect.ImmutableList;
import com.mahdiyar.dosattack.exceptions.GeneralExistsException;
import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.model.dto.bank.BankDto;
import com.mahdiyar.dosattack.model.dto.bank.CreateBankRequestDto;
import com.mahdiyar.dosattack.model.entity.BankEntity;
import com.mahdiyar.dosattack.repository.mysqlRepositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    public List<BankDto> getBanks() {
        List<BankEntity> bankEntities = bankRepository.findAll();
        List<BankDto> bankDtos = ImmutableList.of();
        if (!bankEntities.isEmpty()) {
            bankDtos = bankEntities.stream().map(BankDto::new).collect(Collectors.toList());
        }
        return bankDtos;
    }

    public BankEntity findById(String bankId) throws GeneralNotFoundException {
        Optional<BankEntity> optionalBankEntity = bankRepository.findById(bankId);
        if (!optionalBankEntity.isPresent())
            throw new GeneralNotFoundException("Bank", "id", bankId);
        return optionalBankEntity.get();
    }

    public BankDto getBank(String bankId) throws GeneralNotFoundException {
        BankEntity bankEntity = findById(bankId);
        return new BankDto(bankEntity);
    }


    public BankDto createBank(CreateBankRequestDto requestDto) throws GeneralExistsException {
        if (bankRepository.existsAllByName(requestDto.getName())) {
            throw new GeneralExistsException("Bank", "Name", requestDto.getName());
        }
        BankEntity bankEntity = new BankEntity();
        bankEntity.setBalance(requestDto.getBalance());
        bankEntity.setName(requestDto.getName());
        bankEntity = bankRepository.save(bankEntity);
        return new BankDto(bankEntity);
    }

    public BankDto updateBankBalance(String bankId, Long balance) throws GeneralNotFoundException {
        BankEntity bankEntity = findById(bankId);
        bankEntity.setBalance(balance);
        return new BankDto(bankRepository.save(bankEntity));
    }

    public BankEntity decreaseLoanAmount(BankEntity bankEntity, Long amount) {
        bankEntity.setBalance(bankEntity.getBalance() - amount);
        return bankRepository.save(bankEntity);
    }
}
