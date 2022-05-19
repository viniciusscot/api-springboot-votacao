package com.sicredi.votacao.internal.interactors.votes;

import com.sicredi.votacao.bootstrap.exceptions.VoteAlreadyComputedException;
import com.sicredi.votacao.internal.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FindVoteByAssociateIdAndSchedulleIdThenTrownUseCase {

    private final VoteRepository voteRepository;


    public FindVoteByAssociateIdAndSchedulleIdThenTrownUseCase(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void execute(final String associateId, final String schedulleId) {
        final var vote = this.voteRepository.getByAssociateIdAndSessionId(associateId, schedulleId);

        if (Objects.nonNull(vote))
            throw new VoteAlreadyComputedException(String.format("The associate with cpf %s has already voted in this session.", associateId));

        return;
    }
}
