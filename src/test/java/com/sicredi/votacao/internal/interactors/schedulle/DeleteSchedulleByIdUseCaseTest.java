package com.sicredi.votacao.internal.interactors.schedulle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.repositories.SchedulleRepository;
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
public class DeleteSchedulleByIdUseCaseTest {

    private final SchedulleRepository schedulleRepository;
    private final DeleteSchedulleByIdUseCase deleteSchedulleByIdUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/schedulle.json")
    private Resource schedulleResource;

    @Autowired
    public DeleteSchedulleByIdUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.schedulleRepository = mock(SchedulleRepository.class);
        this.deleteSchedulleByIdUseCase = new DeleteSchedulleByIdUseCase(this.schedulleRepository);
    }

    @Test
    @DisplayName("Should return void when delete schedulle by id")
    void shouldReturnVoidWhenDeleteSchedulleById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Schedulle.class);

        when(this.schedulleRepository.get(anyString())).thenReturn(mockObject);

        doNothing().when(this.schedulleRepository).delete(anyString());

        assertDoesNotThrow(() -> this.deleteSchedulleByIdUseCase.execute(anyString()));
    }
}
