package com.sicredi.votacao.internal.interactors.votes;

import com.sicredi.votacao.internal.entities.VerifyCpf;
import com.sicredi.votacao.internal.repositories.VerifyCpfRepository;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class VerifyCpfUseCase {

    private final VerifyCpfRepository verifyCpfRepository;


    public VerifyCpfUseCase(VerifyCpfRepository verifyCpfRepository) {
        this.verifyCpfRepository = verifyCpfRepository;
    }

    public Boolean execute(final String cpf) {
        final var objOfVerification = this.verifyCpfRepository.isValidAndCanVote(cpf);

        if (objOfVerification.getStatus().equals(VerifyCpf.StatusEnum.ABLE_TO_VOTE))
            return TRUE;

        return FALSE;
    }
}
