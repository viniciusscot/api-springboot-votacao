package com.sicredi.votacao.adapter.transportlayers.restapi;

import com.sicredi.votacao.adapter.transportlayers.mapper.VoteMapper;
import com.sicredi.votacao.adapter.transportlayers.openapi.api.VotesApi;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.VoteInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.VoteResult;
import com.sicredi.votacao.internal.interactors.votes.CreateVoteUseCase;
import com.sicredi.votacao.internal.interactors.votes.GetAllVotesUseCase;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/")
@Api(tags = "Vote")
public class VoteControllerImpl implements VotesApi {

    private final GetAllVotesUseCase getAllVotesUseCase;
    private final CreateVoteUseCase createVoteUseCase;

    public VoteControllerImpl(GetAllVotesUseCase getAllVotesUseCase,
                              CreateVoteUseCase createVoteUseCase) {
        this.getAllVotesUseCase = getAllVotesUseCase;
        this.createVoteUseCase = createVoteUseCase;
    }

    @Override
    public ResponseEntity<VoteResult> createVote(final VoteInput voteInput) {
        final var vote = VoteMapper.INSTANCE.map(voteInput);
        final var voteSaved = this.createVoteUseCase.execute(vote);
        final var response = VoteMapper.INSTANCE.map(voteSaved);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    public ResponseEntity<List<VoteResult>> getAllVotes() {
        final var votes = this.getAllVotesUseCase.execute();
        final var response = votes.stream()
                .map(a -> VoteMapper.INSTANCE.map(a))
                .collect(Collectors.toList());
        return ResponseEntity.status(OK).body(response);
    }
}
