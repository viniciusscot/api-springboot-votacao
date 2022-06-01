package com.sicredi.votacao.internal.repositories;

import com.sicredi.votacao.internal.entities.Session;

public interface KafkaRepository {

    void send(final Session session);
}
