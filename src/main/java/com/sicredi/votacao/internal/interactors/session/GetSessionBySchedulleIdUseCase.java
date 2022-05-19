package com.sicredi.votacao.internal.interactors.session;

import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSessionBySchedulleIdUseCase {

    private final SessionRepository sessionRepository;

    public GetSessionBySchedulleIdUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> execute(final String schedulleId) {
        return sessionRepository.getAllBySchedulleId(schedulleId);
    }
}
