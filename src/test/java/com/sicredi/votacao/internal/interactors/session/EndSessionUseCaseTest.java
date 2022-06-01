package com.sicredi.votacao.internal.interactors.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.interactors.kafka.SendMessageUseCase;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EndSessionUseCaseTest {

    private final GetFinishedSessionsUseCase getFinishedSessionsUseCase;
    private final UpdateSessionUseCase updateSessionUseCase;
    private final SendMessageUseCase sendMessageUseCase;
    private final EndSessionUseCase endSessionUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Autowired
    public EndSessionUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.getFinishedSessionsUseCase = mock(GetFinishedSessionsUseCase.class);
        this.updateSessionUseCase = mock(UpdateSessionUseCase.class);
        this.sendMessageUseCase = mock(SendMessageUseCase.class);
        this.endSessionUseCase = new EndSessionUseCase(this.getFinishedSessionsUseCase, this.updateSessionUseCase, this.sendMessageUseCase);
    }

    @Test
    @DisplayName("Should void when end session")
    void shouldVoidWhenEndSession() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Session.class);

        when(this.getFinishedSessionsUseCase.execute()).thenReturn(Collections.singletonList(mockObject));
        when(this.updateSessionUseCase.execute(any(Session.class))).thenReturn(mockObject);
        doNothing().when(this.sendMessageUseCase).execute(mockObject);

        assertDoesNotThrow(() -> this.endSessionUseCase.execute());
    }
}
