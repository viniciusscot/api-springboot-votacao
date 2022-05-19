package com.sicredi.votacao.internal.interactors.schedulle;

import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.repositories.SchedulleRepository;
import org.springframework.stereotype.Service;

@Service
public class GetSchedulleBySessionIdUseCase {

    private final SchedulleRepository schedulleRepository;

    public GetSchedulleBySessionIdUseCase(SchedulleRepository schedulleRepository) {
        this.schedulleRepository = schedulleRepository;
    }

    public Schedulle execute(final String schedulleId) {
        return this.schedulleRepository.get(schedulleId);
    }

}