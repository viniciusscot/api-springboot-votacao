package com.sicredi.votacao.internal.interactors.associate;

import com.sicredi.votacao.internal.entities.Associate;
import com.sicredi.votacao.internal.repositories.AssociateRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAssociateUseCase {

    private final AssociateRepository associateRepository;

    public CreateAssociateUseCase(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    public Associate execute(final Associate associate) {
        return this.associateRepository.save(associate);
    }

}