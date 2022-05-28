package com.sicredi.votacao.internal.interactors.votes;

import com.sicredi.votacao.internal.entities.VerifyCpf;
import com.sicredi.votacao.internal.repositories.VerifyCpfRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class VerifyCpfUseCaseTest {

    private final VerifyCpfRepository verifyCpfRepository;
    private final VerifyCpfUseCase verifyCpfUseCase;

    public VerifyCpfUseCaseTest() {
        this.verifyCpfRepository = mock(VerifyCpfRepository.class);
        this.verifyCpfUseCase = new VerifyCpfUseCase(this.verifyCpfRepository);
    }

    @Test
    @DisplayName("Should return true when verify cpf")
    void shouldReturnTrueWhenVerifyCpf() {
        final var mockObject = new VerifyCpf().setStatus(VerifyCpf.StatusEnum.ABLE_TO_VOTE);

        when(this.verifyCpfRepository.isValidAndCanVote(anyString())).thenReturn(mockObject);

        var useCaseResponse = this.verifyCpfUseCase.execute(anyString());

        assertNotNull(useCaseResponse);
        assertTrue(useCaseResponse);
    }

    @Test
    @DisplayName("Should return false when verify cpf")
    void shouldReturnFalseWhenVerifyCpf() {
        final var mockObject = new VerifyCpf().setStatus(VerifyCpf.StatusEnum.UNABLE_TO_VOTE);

        when(this.verifyCpfRepository.isValidAndCanVote(anyString())).thenReturn(mockObject);

        var useCaseResponse = this.verifyCpfUseCase.execute(anyString());

        assertNotNull(useCaseResponse);
        assertFalse(useCaseResponse);
    }
}
