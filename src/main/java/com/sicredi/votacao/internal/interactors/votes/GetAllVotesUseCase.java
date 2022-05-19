package com.sicredi.votacao.internal.interactors.votes;

import com.sicredi.votacao.internal.entities.Vote;
import com.sicredi.votacao.internal.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllVotesUseCase {

    private final VoteRepository voteRepository;

    public GetAllVotesUseCase(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> execute() {
        return this.voteRepository.getAll();
    }

}