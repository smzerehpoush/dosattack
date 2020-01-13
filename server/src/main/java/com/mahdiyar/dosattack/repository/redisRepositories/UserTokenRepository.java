package com.mahdiyar.dosattack.repository.redisRepositories;

import com.mahdiyar.dosattack.model.entity.UserTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface UserTokenRepository extends CrudRepository<UserTokenEntity, String> {
}
