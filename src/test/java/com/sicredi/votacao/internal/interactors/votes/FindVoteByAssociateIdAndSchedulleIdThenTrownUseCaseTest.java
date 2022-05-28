package com.sicredi.votacao.internal.interactors.votes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.bootstrap.exceptions.VoteAlreadyComputedException;
import com.sicredi.votacao.internal.entities.Vote;
import com.sicredi.votacao.internal.repositories.VoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FindVoteByAssociateIdAndSchedulleIdThenTrownUseCaseTest {

    private final VoteRepository voteRepository;
    private final FindVoteByAssociateIdAndSchedulleIdThenTrownUseCase findVoteByAssociateIdAndSchedulleIdThenTrownUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/vote.json")
    private Resource voteResource;

    @Autowired
    public FindVoteByAssociateIdAndSchedulleIdThenTrownUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.voteRepository = mock(VoteRepository.class);
        this.findVoteByAssociateIdAndSchedulleIdThenTrownUseCase = new FindVoteByAssociateIdAndSchedulleIdThenTrownUseCase(this.voteRepository);
    }

    @Test
    @DisplayName("Should return void when find vote by associate id and schedulle id")
    void shouldReturnVoidWhenFindVoteByAssociateIdAndSchedulleId() {
        when(this.voteRepository.getByAssociateIdAndSessionId(anyString(), anyString())).thenReturn(null);

        assertDoesNotThrow(() -> this.findVoteByAssociateIdAndSchedulleIdThenTrownUseCase.execute(anyString(), anyString()));
    }

    @Test
    @DisplayName("Should throw exception when find vote by associate id and schedulle id but this associate is already voted")
    void shouldThrowExceptionWhenFindVoteByAssociateIdAndSchedulleIdButThisAssociateIsAlreadyVoted() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.voteResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Vote.class);

        when(this.voteRepository.getByAssociateIdAndSessionId(anyString(), anyString())).thenReturn(mockObject);

        assertThrows(VoteAlreadyComputedException.class, () -> this.findVoteByAssociateIdAndSchedulleIdThenTrownUseCase.execute(mockObject.getAssociateId(), mockObject.getSchedulleId()));
    }
}
