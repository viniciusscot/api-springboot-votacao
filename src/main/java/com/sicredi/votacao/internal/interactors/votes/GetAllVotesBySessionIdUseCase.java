package com.sicredi.votacao.internal.interactors.votes;

import com.sicredi.votacao.internal.entities.Vote;
import com.sicredi.votacao.internal.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllVotesBySessionIdUseCase {

    private final VoteRepository voteRepository;

    public GetAllVotesBySessionIdUseCase(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> execute(final String sessionId) {
        return this.voteRepository.getAllBySessionId(sessionId);
    }

}