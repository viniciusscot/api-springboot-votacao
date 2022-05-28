package com.sicredi.votacao.adapter.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.VerifyCpfGateway;
import com.sicredi.votacao.adapter.datasources.services.data.response.VerifyCpfResponse;
import com.sicredi.votacao.bootstrap.exceptions.VoteNotAuthorizedException;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.util.HashMap;

import static feign.FeignException.FeignClientException;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class VerifyCpfDataSourceTest {

    private final VerifyCpfGateway verifyCpfGateway;
    private final VerifyCpfDataSource verifyCpfDataSource;
    private final ObjectMapper objectMapper;

    @Value("classpath:__files/verify-cpf.json")
    private Resource verifyCpfResource;

    @Autowired
    public VerifyCpfDataSourceTest(ObjectMapper objectMapper) {
        this.verifyCpfGateway = Mockito.mock(VerifyCpfGateway.class);
        this.verifyCpfDataSource = new VerifyCpfDataSource(this.verifyCpfGateway);
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should return a verifyCpf when call isValidAndCanVote with a valid cpf")
    void shouldReturnVerifyCpfWhenCallIsValidAndCanVoteWithValidCpf() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.verifyCpfResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, VerifyCpfResponse.class);

        when(this.verifyCpfGateway.isValidAndCanVote(anyString())).thenReturn(mockObject);

        var dataSourceResponse = this.verifyCpfDataSource.isValidAndCanVote(anyString());

        assertNotNull(dataSourceResponse);
        assertThat(mockObject.getStatus().toString(), equalTo(dataSourceResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("Should throw exception when call isValidAndCanVote with invalid cpf")
    void shouldThrowExceptionWhenCallIsValidAndCanVoteWithInValidCpf() {
        Request request = Request.create(Request.HttpMethod.GET, "url",
                new HashMap<>(), null, new RequestTemplate());

        when(this.verifyCpfGateway.isValidAndCanVote(anyString())).thenThrow(new FeignException.NotFound("", request, null));

        assertThrows(VoteNotAuthorizedException.class, () -> this.verifyCpfDataSource.isValidAndCanVote(anyString()));
    }

    @Test
    @DisplayName("Should throw any exception when call isValidAndCanVote with valid cpf")
    void shouldThrowAnyExceptionWhenCallIsValidAndCanVoteWithValidCpf() {
        when(this.verifyCpfGateway.isValidAndCanVote(anyString())).thenThrow(FeignClientException.class);

        assertThrows(FeignClientException.class, () -> this.verifyCpfDataSource.isValidAndCanVote(anyString()));
    }
}