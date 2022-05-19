package com.sicredi.votacao.adapter.datasources;

import com.sicredi.votacao.adapter.datasources.services.VerifyCpfGateway;
import com.sicredi.votacao.adapter.datasources.services.mapper.VerificaCpfMapper;
import com.sicredi.votacao.bootstrap.exceptions.VoteNotAuthorizedException;
import com.sicredi.votacao.internal.entities.VerifyCpf;
import com.sicredi.votacao.internal.repositories.VerifyCpfRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static feign.FeignException.FeignClientException;

@Component
public class VerifyCpfDataSource implements VerifyCpfRepository {

    private final VerifyCpfGateway verifyCpfGateway;

    public VerifyCpfDataSource(VerifyCpfGateway verifyCpfGateway) {
        this.verifyCpfGateway = verifyCpfGateway;
    }

    @Override
    public VerifyCpf isValidAndCanVote(String cpf) {
        try {
            final var cr = verifyCpfGateway.isValidAndCanVote(cpf);

            return VerificaCpfMapper.INSTANCE.map(cr);
        } catch (FeignClientException ex) {
            if (HttpStatus.NOT_FOUND.value() == ex.status())
                throw new VoteNotAuthorizedException(String.format("The cpf of number %s is invalid", cpf));

            throw ex;

        }
    }
}
