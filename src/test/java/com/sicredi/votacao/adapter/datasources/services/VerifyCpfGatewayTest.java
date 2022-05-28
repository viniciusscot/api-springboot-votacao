package com.sicredi.votacao.adapter.datasources.services;

import com.sicredi.votacao.adapter.datasources.services.data.response.VerifyCpfResponse;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {"webservices.port=${wiremock.server.port}"})
public class VerifyCpfGatewayTest {
    private final VerifyCpfGateway verifyCpfGateway;

    private static final String CPF = "01234567890";

    private static final String INVALID_CPF = "invalid_cpf";

    @Autowired
    public VerifyCpfGatewayTest(VerifyCpfGateway verifyCpfGateway) {
        this.verifyCpfGateway = verifyCpfGateway;
    }

    @Test
    @DisplayName("Should return status when send a valid CPF")
    void shouldReturnStatusWhenSendAValidCpf() {

        final var response = this.verifyCpfGateway.isValidAndCanVote(CPF);

        assertNotNull(response);
        assertEquals(VerifyCpfResponse.StatusEnum.ABLE_TO_VOTE, response.getStatus());

    }

    @Test
    @DisplayName("Should return Feign Exception when send a invalid CPF")
    void shouldReturnStatusWhenSendAInValidCpf() {

        assertThrows(FeignException.class, () -> this.verifyCpfGateway.isValidAndCanVote(INVALID_CPF));

    }

}
