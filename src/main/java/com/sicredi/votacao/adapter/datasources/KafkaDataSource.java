package com.sicredi.votacao.adapter.datasources;

import com.sicredi.votacao.adapter.datasources.services.KafkaProducer;
import com.sicredi.votacao.adapter.datasources.services.mapper.SessionMapper;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.KafkaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaDataSource implements KafkaRepository {

    private final KafkaProducer kafkaProducer;

    @Value("${services.sicredi.kafkaTopic}")
    private String topic;

    public KafkaDataSource(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void send(final Session session) {
        this.kafkaProducer.send(this.topic, SessionMapper.INSTANCE.mapEvent(session));
        return;
    }
}
