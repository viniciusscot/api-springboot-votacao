package com.sicredi.votacao.adapter.datasources.services;

import com.sicredi.votacao.adapter.datasources.services.model.SessionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MongoSessionRepository extends MongoRepository<SessionModel, String> {

    Optional<SessionModel> findBySchedulleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String schedulleId,
                                                                                                OffsetDateTime startDate,
                                                                                                OffsetDateTime endDate);

    List<SessionModel> findAllBySchedulleId(String schedulleId);

    List<SessionModel> findAllByFinishedAndAndEndDateLessThanEqual(Boolean finished, OffsetDateTime now);

}
