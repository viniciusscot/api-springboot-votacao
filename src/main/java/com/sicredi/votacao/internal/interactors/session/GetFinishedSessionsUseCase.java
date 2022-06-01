package com.sicredi.votacao.internal.interactors.session;

import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFinishedSessionsUseCase {

    private final SessionRepository sessionRepository;

    public GetFinishedSessionsUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> execute() {
        return this.sessionRepository.getFinishedSessions();
    }
}
