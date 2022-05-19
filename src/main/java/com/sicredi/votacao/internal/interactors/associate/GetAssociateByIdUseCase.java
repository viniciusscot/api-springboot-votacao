package com.sicredi.votacao.internal.interactors.associate;

import com.sicredi.votacao.internal.entities.Associate;
import com.sicredi.votacao.internal.repositories.AssociateRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAssociateByIdUseCase {

    private final AssociateRepository associateRepository;

    public GetAssociateByIdUseCase(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    public Associate execute(final String associateId) {
        return this.associateRepository.get(associateId);
    }

}