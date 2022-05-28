package com.sicredi.votacao.internal.interactors.schedulle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.entities.Vote;
import com.sicredi.votacao.internal.interactors.session.GetSessionBySchedulleIdUseCase;
import com.sicredi.votacao.internal.interactors.votes.GetAllVotesBySessionIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GetResultsOfSchedulleSessionVoteUseCaseTest {

    private final GetSessionBySchedulleIdUseCase getSessionBySchedulleIdUseCase;
    private final GetAllVotesBySessionIdUseCase getAllVotesBySessionIdUseCase;
    private final GetResultsOfSchedulleSessionVoteUseCase getResultsOfSchedulleSessionVoteUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Value("classpath:datasource/sicredi/payload/vote.json")
    private Resource votesResource;

    @Autowired
    public GetResultsOfSchedulleSessionVoteUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.getSessionBySchedulleIdUseCase = mock(GetSessionBySchedulleIdUseCase.class);
        this.getAllVotesBySessionIdUseCase = mock(GetAllVotesBySessionIdUseCase.class);
        this.getResultsOfSchedulleSessionVoteUseCase = new GetResultsOfSchedulleSessionVoteUseCase(this.getSessionBySchedulleIdUseCase, this.getAllVotesBySessionIdUseCase);
    }

    @Test
    @DisplayName("Should return Results with result YES when get result")
    void shouldReturnResultsWithResultYesWhenGetResult() throws Exception {
        final var mockSessionString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);
        var mockSession = objectMapper.readValue(mockSessionString, Session.class);
        final var mockVoteString = StreamUtils.copyToString(this.votesResource.getInputStream(), UTF_8);
        var mockVote = objectMapper.readValue(mockVoteString, Vote.class);

        when(this.getSessionBySchedulleIdUseCase.execute(anyString())).thenReturn(Arrays.asList(mockSession));
        when(this.getAllVotesBySessionIdUseCase.execute(mockSession.getId())).thenReturn(Arrays.asList(mockVote));

        var useCaseResponse = this.getResultsOfSchedulleSessionVoteUseCase.execute(anyString());

        assertNotNull(useCaseResponse);
        assertThat(Integer.valueOf(1), equalTo(useCaseResponse.size()));
        assertThat("YES", equalTo(useCaseResponse.get(0).getResult()));
    }

    @Test
    @DisplayName("Should return Results with result NO when get result")
    void shouldReturnResultsWithResultNoWhenGetResult() throws Exception {
        final var mockSessionString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);
        var mockSession = objectMapper.readValue(mockSessionString, Session.class);
        final var mockVoteString = StreamUtils.copyToString(this.votesResource.getInputStream(), UTF_8);
        var mockVote = objectMapper.readValue(mockVoteString, Vote.class).setDecision(Boolean.FALSE);

        when(this.getSessionBySchedulleIdUseCase.execute(anyString())).thenReturn(Arrays.asList(mockSession));
        when(this.getAllVotesBySessionIdUseCase.execute(mockSession.getId())).thenReturn(Arrays.asList(mockVote));

        var useCaseResponse = this.getResultsOfSchedulleSessionVoteUseCase.execute(anyString());

        assertNotNull(useCaseResponse);
        assertThat(Integer.valueOf(1), equalTo(useCaseResponse.size()));
        assertThat("NO", equalTo(useCaseResponse.get(0).getResult()));
    }

    @Test
    @DisplayName("Should return Results with result DRAW when get result")
    void shouldReturnResultsWithResultDrawWhenGetResult() throws Exception {
        final var mockSessionString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);
        var mockSession = objectMapper.readValue(mockSessionString, Session.class);

        when(this.getSessionBySchedulleIdUseCase.execute(anyString())).thenReturn(Arrays.asList(mockSession));
        when(this.getAllVotesBySessionIdUseCase.execute(mockSession.getId())).thenReturn(Arrays.asList());

        var useCaseResponse = this.getResultsOfSchedulleSessionVoteUseCase.execute(anyString());

        assertNotNull(useCaseResponse);
        assertThat(Integer.valueOf(1), equalTo(useCaseResponse.size()));
        assertThat("DRAW", equalTo(useCaseResponse.get(0).getResult()));
    }
}
