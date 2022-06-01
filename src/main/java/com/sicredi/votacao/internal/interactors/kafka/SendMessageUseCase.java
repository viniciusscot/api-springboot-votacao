package com.sicredi.votacao.internal.interactors.kafka;

import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.KafkaRepository;
import org.springframework.stereotype.Service;

@Service
public class SendMessageUseCase {
    private final KafkaRepository kafkaRepository;

    public SendMessageUseCase(KafkaRepository kafkaRepository) {
        this.kafkaRepository = kafkaRepository;
    }

    public void execute(final Session session) {
        this.kafkaRepository.send(session);

        return;
    }
}
