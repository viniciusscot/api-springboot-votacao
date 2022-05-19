package com.sicredi.votacao.internal.interactors.associate;

import com.sicredi.votacao.internal.repositories.AssociateRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteAssociateByIdUseCase {

    private final AssociateRepository associateRepository;

    public DeleteAssociateByIdUseCase(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    public void execute(final String associateId) {
        this.associateRepository.get(associateId);
        
        this.associateRepository.delete(associateId);
    }

}