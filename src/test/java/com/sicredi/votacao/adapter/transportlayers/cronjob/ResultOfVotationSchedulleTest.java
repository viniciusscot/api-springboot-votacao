package com.sicredi.votacao.adapter.transportlayers.cronjob;

import com.sicredi.votacao.internal.interactors.session.EndSessionUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ResultOfVotationSchedulleTest {

    private final EndSessionUseCase endSessionUseCase;
    private final ResultOfVotationSchedulle resultOfVotationSchedulle;

    @Value("classpath:datasource/sicredi/payload/associate.json")
    private Resource associateResource;

    @Autowired
    public ResultOfVotationSchedulleTest() {
        this.endSessionUseCase = mock(EndSessionUseCase.class);
        this.resultOfVotationSchedulle = new ResultOfVotationSchedulle(this.endSessionUseCase);
    }

    @Test
    @DisplayName("Should return void when finished votation")
    void shouldReturnVoidWhenFinishedVotation() {
        doNothing().when(this.endSessionUseCase).execute();

        assertDoesNotThrow(() -> this.resultOfVotationSchedulle.finishedVotation());
    }
}
