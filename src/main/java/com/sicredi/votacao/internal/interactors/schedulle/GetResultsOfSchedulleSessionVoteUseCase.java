package com.sicredi.votacao.internal.interactors.schedulle;

import com.sicredi.votacao.internal.entities.ResultOfSchedulle;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.interactors.session.GetSessionBySchedulleIdUseCase;
import com.sicredi.votacao.internal.interactors.votes.GetAllVotesBySessionIdUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetResultsOfSchedulleSessionVoteUseCase {

    private final GetSessionBySchedulleIdUseCase getSessionBySchedulleIdUseCase;
    private final GetAllVotesBySessionIdUseCase getAllVotesBySessionIdUseCase;

    public GetResultsOfSchedulleSessionVoteUseCase(GetSessionBySchedulleIdUseCase getSessionBySchedulleIdUseCase,
                                                   GetAllVotesBySessionIdUseCase getAllVotesBySessionIdUseCase) {
        this.getSessionBySchedulleIdUseCase = getSessionBySchedulleIdUseCase;
        this.getAllVotesBySessionIdUseCase = getAllVotesBySessionIdUseCase;
    }

    public List<ResultOfSchedulle> execute(final String schedulleId) {
        return this.getSessionBySchedulleIdUseCase.execute(schedulleId).stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private ResultOfSchedulle map(Session session) {
        final var votes = this.getAllVotesBySessionIdUseCase.execute(session.getId());

        Long votesYes = votes.stream().filter(v -> v.getDecision().equals(true)).count();
        Long votesNo = votes.stream().filter(v -> v.getDecision().equals(false)).count();

        return new ResultOfSchedulle()
                .setSession(session)
                .setVotes(Long.valueOf(votes.size()))
                .setVotesYes(votesYes)
                .setVotesNo(votesNo)
                .setResult(votesNo.equals(votesYes) ? "DRAW" : votesNo > votesYes ? "NO" : "YES");
    }
}
