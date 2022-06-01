package com.sicredi.votacao.internal.interactors.session;

import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UpdateSessionUseCase {

    private final SessionRepository sessionRepository;

    public UpdateSessionUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session execute(final Session session) {
        var actualSession = this.sessionRepository.get(session.getId());

        BeanUtils.copyProperties(session, actualSession, "id");

        return this.sessionRepository.save(actualSession);
    }

}