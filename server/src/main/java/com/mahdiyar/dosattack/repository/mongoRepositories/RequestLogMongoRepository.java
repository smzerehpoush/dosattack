package com.mahdiyar.dosattack.repository.mongoRepositories;

import com.mahdiyar.dosattack.model.entity.RequestLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestLogMongoRepository extends MongoRepository<RequestLogEntity, UUID> {
}
