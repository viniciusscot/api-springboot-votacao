package com.sicredi.votacao.internal.interactors.schedulle;

import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.repositories.SchedulleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UpdateSchedulleUseCase {

    private final SchedulleRepository schedulleRepository;

    public UpdateSchedulleUseCase(SchedulleRepository schedulleRepository) {
        this.schedulleRepository = schedulleRepository;
    }

    public Schedulle execute(final String schedulleId, final Schedulle schedulle) {
        var actualSchedulle = this.schedulleRepository.get(schedulleId);

        BeanUtils.copyProperties(schedulle, actualSchedulle, "id");

        return this.schedulleRepository.save(actualSchedulle);
    }

}