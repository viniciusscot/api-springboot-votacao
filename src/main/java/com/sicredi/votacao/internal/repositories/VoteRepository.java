package com.sicredi.votacao.internal.repositories;

import com.sicredi.votacao.internal.entities.Vote;

import java.util.List;

public interface VoteRepository {

    List<Vote> getAll();

    Vote save(final Vote vote);

    Vote getByAssociateIdAndSessionId(final String associateId, final String sessionId);

    List<Vote> getAllBySessionId(final String sessionId);
}
