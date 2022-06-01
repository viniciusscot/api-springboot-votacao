package com.sicredi.votacao.internal.repositories;

import com.sicredi.votacao.internal.entities.Session;

import java.time.OffsetDateTime;
import java.util.List;

public interface SessionRepository {

    Session save(final Session session);

    Session getBySchedulleIdAndStartDateAndEndDate(final String schedulleId, final OffsetDateTime date);

    List<Session> getAllBySchedulleId(final String schedulleId);

    List<Session> getFinishedSessions();

    Session get(String id);
}
