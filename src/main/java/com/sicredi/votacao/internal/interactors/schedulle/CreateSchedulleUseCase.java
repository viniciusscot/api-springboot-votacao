package com.sicredi.votacao.internal.interactors.schedulle;

import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.repositories.SchedulleRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateSchedulleUseCase {

    private final SchedulleRepository schedulleRepository;

    public CreateSchedulleUseCase(SchedulleRepository schedulleRepository) {
        this.schedulleRepository = schedulleRepository;
    }

    public Schedulle execute(final Schedulle schedulle) {
        return this.schedulleRepository.save(schedulle);
    }

}