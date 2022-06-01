package com.sicredi.votacao.adapter.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.KafkaProducer;
import com.sicredi.votacao.adapter.datasources.services.event.SessionEvent;
import com.sicredi.votacao.internal.entities.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class KafkaDataSourceTest {

    private final KafkaProducer kafkaProducer;
    private final KafkaDataSource kafkaDataSource;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Autowired
    public KafkaDataSourceTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.kafkaProducer = Mockito.mock(KafkaProducer.class);
        this.kafkaDataSource = new KafkaDataSource(this.kafkaProducer);
    }

    @Test
    @DisplayName("Should return void when send kafka message")
    void shouldReturnVoidWhenSend() throws IOException {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Session.class);

        doNothing().when(this.kafkaProducer).send(anyString(), any(SessionEvent.class));

        assertDoesNotThrow(() -> this.kafkaDataSource.send(mockObject));
    }
}
