package com.sicredi.votacao.adapter.datasources;

import com.sicredi.votacao.adapter.datasources.services.MongoSessionRepository;
import com.sicredi.votacao.adapter.datasources.services.mapper.SessionMapper;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionDataSource implements SessionRepository {

    private final MongoSessionRepository repository;

    public SessionDataSource(MongoSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Session save(final Session session) {
        final var sessionModel = SessionMapper.INSTANCE.map(session);
        final var sessionSaved = this.repository.save(sessionModel);
        return SessionMapper.INSTANCE.map(sessionSaved);
    }

    @Override
    public Session getBySchedulleIdAndStartDateAndEndDate(final String schedulleId,
                                                          final OffsetDateTime date) {
        final var session =
                this.repository.findBySchedulleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(schedulleId, date, date)
                        .orElse(null);

        return SessionMapper.INSTANCE.map(session);
    }

    @Override
    public List<Session> getAllBySchedulleId(final String schedulleId) {
        return this.repository.findAllBySchedulleId(schedulleId)
                .stream().map(s -> SessionMapper.INSTANCE.map(s))
                .collect(Collectors.toList());
    }
}
