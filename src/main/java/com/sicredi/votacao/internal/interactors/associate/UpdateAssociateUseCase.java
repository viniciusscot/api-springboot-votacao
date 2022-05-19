package com.sicredi.votacao.internal.interactors.associate;

import com.sicredi.votacao.internal.entities.Associate;
import com.sicredi.votacao.internal.repositories.AssociateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UpdateAssociateUseCase {

    private final AssociateRepository associateRepository;

    public UpdateAssociateUseCase(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    public Associate execute(final String associateId, final Associate associate) {
        var actualAssociate = this.associateRepository.get(associateId);

        BeanUtils.copyProperties(associate, actualAssociate, "id");

        return this.associateRepository.save(actualAssociate);
    }

}