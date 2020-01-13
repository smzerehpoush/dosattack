package com.mahdiyar.dosattack.repository.mysqlRepositories;

import com.mahdiyar.dosattack.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsAllByUsername(String username);

    UserEntity findByUsernameAndHashedPassword(String username, String hashedPassword);

}
