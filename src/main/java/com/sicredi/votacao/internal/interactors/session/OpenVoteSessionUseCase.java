package com.sicredi.votacao.internal.interactors.session;

import com.sicredi.votacao.bootstrap.exceptions.EntityInUseException;
import com.sicredi.votacao.bootstrap.utils.DateUtils;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.interactors.schedulle.GetSchedulleByIdUseCase;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class OpenVoteSessionUseCase {

    private final SessionRepository sessionRepository;
    private final GetSchedulleByIdUseCase getSchedulleByIdUseCase;
    private final GetSessionBySchedulleIdAndStartDateAndEndDateUseCase getSessionBySchedulleIdAndStartDateAndEndDateUseCase;
    private final DateUtils dateUtils;

    public OpenVoteSessionUseCase(SessionRepository sessionRepository,
                                  GetSchedulleByIdUseCase getSchedulleByIdUseCase,
                                  GetSessionBySchedulleIdAndStartDateAndEndDateUseCase getSessionBySchedulleIdAndStartDateAndEndDateUseCase,
                                  DateUtils dateUtils) {
        this.sessionRepository = sessionRepository;
        this.getSchedulleByIdUseCase = getSchedulleByIdUseCase;
        this.getSessionBySchedulleIdAndStartDateAndEndDateUseCase = getSessionBySchedulleIdAndStartDateAndEndDateUseCase;
        this.dateUtils = dateUtils;
    }

    public Session execute(Session session) {
        final var now = this.dateUtils.getDate();

        this.getSchedulleByIdUseCase.execute(session.getSchedulleId());

        session.setStartDate(now.toInstant()
                        .atOffset(ZoneOffset.UTC))
                .setEndDate(this.dateUtils.addMinutesToDate(now, session.getDurationInMinutes())
                        .toInstant()
                        .atOffset(ZoneOffset.UTC));

        final var existingSession = this.getSessionBySchedulleIdAndStartDateAndEndDateUseCase.execute(session.getSchedulleId(), session.getStartDate());

        if (Objects.nonNull(existingSession))
            throw new EntityInUseException("There is already an active session for this schedulle");


        return this.sessionRepository.save(session);
    }
}
