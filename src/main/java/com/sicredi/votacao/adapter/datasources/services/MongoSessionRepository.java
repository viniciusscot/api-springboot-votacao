package com.sicredi.votacao.adapter.datasources.services;

import com.sicredi.votacao.adapter.datasources.services.model.SessionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface MongoSessionRepository extends MongoRepository<SessionModel, String> {

    Optional<SessionModel> findBySchedulleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String schedulleId,
                                                                                                OffsetDateTime startDate,
                                                                                                OffsetDateTime endDate);

    List<SessionModel> findAllBySchedulleId(String schedulleId);

}
