package com.sicredi.votacao.internal.interactors.schedulle;

import com.sicredi.votacao.internal.repositories.SchedulleRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteSchedulleByIdUseCase {

    private final SchedulleRepository schedulleRepository;

    public DeleteSchedulleByIdUseCase(SchedulleRepository schedulleRepository) {
        this.schedulleRepository = schedulleRepository;
    }

    public void execute(final String schedulleId) {
        this.schedulleRepository.get(schedulleId);

        this.schedulleRepository.delete(schedulleId);
    }

}