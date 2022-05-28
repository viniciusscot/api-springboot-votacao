package com.sicredi.votacao.adapter.datasources.services;

import com.sicredi.votacao.adapter.datasources.services.model.VoteModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoVoteRepository extends MongoRepository<VoteModel, String> {

    Optional<VoteModel> findByAssociateIdAndSchedulleId(String associateId, String schedulleId);

    List<VoteModel> findAllBySessionId(String sessionId);

}
