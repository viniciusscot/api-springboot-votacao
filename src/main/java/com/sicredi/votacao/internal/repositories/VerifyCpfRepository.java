package com.sicredi.votacao.internal.repositories;

import com.sicredi.votacao.internal.entities.VerifyCpf;

public interface VerifyCpfRepository {

    VerifyCpf isValidAndCanVote(final String cpf);
}
