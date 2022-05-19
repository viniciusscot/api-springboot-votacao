package com.sicredi.votacao.internal.interactors.session;

import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class GetSessionBySchedulleIdAndStartDateAndEndDateUseCase {

    private final SessionRepository sessionRepository;

    public GetSessionBySchedulleIdAndStartDateAndEndDateUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session execute(final String schedulleId, final OffsetDateTime date) {
        return sessionRepository.getBySchedulleIdAndStartDateAndEndDate(schedulleId, date);
    }
}
