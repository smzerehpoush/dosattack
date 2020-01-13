package com.mahdiyar.dosattack.repository.mysqlRepositories;

import com.mahdiyar.dosattack.model.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, String> {
}
