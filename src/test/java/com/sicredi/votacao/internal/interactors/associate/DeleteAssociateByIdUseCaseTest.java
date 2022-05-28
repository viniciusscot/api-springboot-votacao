package com.sicredi.votacao.internal.interactors.associate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Associate;
import com.sicredi.votacao.internal.repositories.AssociateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DeleteAssociateByIdUseCaseTest {

    private final AssociateRepository associateRepository;
    private final DeleteAssociateByIdUseCase deleteAssociateByIdUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/associate.json")
    private Resource associateResource;

    @Autowired
    public DeleteAssociateByIdUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.associateRepository = mock(AssociateRepository.class);
        this.deleteAssociateByIdUseCase = new DeleteAssociateByIdUseCase(this.associateRepository);
    }

    @Test
    @DisplayName("Should return void when delete associate by id")
    void shouldReturnVoidWhenDeleteAssociateById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Associate.class);

        when(this.associateRepository.get(anyString())).thenReturn(mockObject);

        doNothing().when(this.associateRepository).delete(anyString());

        assertDoesNotThrow(() -> this.deleteAssociateByIdUseCase.execute(anyString()));
    }
}
