package com.sicredi.votacao.internal.interactors.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GetFinishedSessionsUseCaseTest {

    private final SessionRepository sessionRepository;
    private final GetFinishedSessionsUseCase getFinishedSessionsUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Autowired
    public GetFinishedSessionsUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.sessionRepository = mock(SessionRepository.class);
        this.getFinishedSessionsUseCase = new GetFinishedSessionsUseCase(this.sessionRepository);
    }

    @Test
    @DisplayName("Should return a session's list when get finished sessions")
    void shouldReturnSessionListWhenGetFinishedSessions() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Session.class);

        when(this.sessionRepository.getFinishedSessions()).thenReturn(Collections.singletonList(mockObject));

        var useCaseResponse = this.getFinishedSessionsUseCase.execute();

        assertNotNull(useCaseResponse);
        assertThat(Integer.valueOf(1), CoreMatchers.equalTo(useCaseResponse.size()));
    }
}
