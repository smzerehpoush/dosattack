package com.mahdiyar.dosattack.repository.mysqlRepositories;

import com.mahdiyar.dosattack.model.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface BankRepository extends JpaRepository<BankEntity, String> {
    boolean existsAllByName(String name);
}
