package com.sicredi.votacao.adapter.datasources.services;

import com.sicredi.votacao.adapter.datasources.services.data.response.VerifyCpfResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Gateway responsible for communicating with Cpf API
 */
@FeignClient(name = "verifyCpfGateway", url = "${services.sicredi.cpfGateway}")
@Headers("Content-Type: application/json")
public interface VerifyCpfGateway {

    @GetMapping(path = "/users/{cpf}")
    VerifyCpfResponse isValidAndCanVote(@PathVariable String cpf);

}
