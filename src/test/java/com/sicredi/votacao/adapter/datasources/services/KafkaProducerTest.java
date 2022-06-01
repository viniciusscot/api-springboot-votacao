package com.sicredi.votacao.adapter.datasources.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.event.SessionEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.StreamUtils;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaProducerTest {
    private final KafkaProducer producer;
    private final ObjectMapper objectMapper;

    @Value("${services.sicredi.kafkaTopic}")
    private String topic;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Autowired
    public KafkaProducerTest(KafkaProducer producer, ObjectMapper objectMapper) {
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Given embedded kafka broker when sending to simple producer the message received")
    void shouldReturnStatusWhenSendAValidCpf() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SessionEvent.class);

        assertDoesNotThrow(() -> this.producer.send(this.topic, mockObject));
    }

}
