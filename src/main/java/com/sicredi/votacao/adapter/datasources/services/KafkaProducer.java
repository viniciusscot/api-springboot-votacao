package com.sicredi.votacao.adapter.datasources.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.event.SessionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(final String topic, final SessionEvent payload) {
        try {

            this.kafkaTemplate.send(topic, this.objectMapper.writeValueAsString(payload));
        } catch (RuntimeException | JsonProcessingException e) {
            System.out.println("se fodeu");
        }
    }
}
