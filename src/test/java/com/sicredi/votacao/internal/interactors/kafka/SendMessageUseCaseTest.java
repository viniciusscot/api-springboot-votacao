package com.sicredi.votacao.internal.interactors.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.KafkaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SendMessageUseCaseTest {
    private final KafkaRepository kafkaRepository;
    private final SendMessageUseCase sendMessageUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Autowired
    public SendMessageUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.kafkaRepository = mock(KafkaRepository.class);
        this.sendMessageUseCase = new SendMessageUseCase(this.kafkaRepository);
    }

    @Test
    @DisplayName("Should return void when send message")
    void shouldReturnVoidWhenSendMessage() throws IOException {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Session.class);

        doNothing().when(this.kafkaRepository).send(any(Session.class));

        assertDoesNotThrow(() -> this.sendMessageUseCase.execute(mockObject));
    }
}
