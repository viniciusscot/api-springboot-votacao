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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GetSchedulleByIdUseCaseTest {

    private final SchedulleRepository schedulleRepository;
    private final GetSchedulleByIdUseCase getSchedulleByIdUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/schedulle.json")
    private Resource schedulleResource;

    @Autowired
    public GetSchedulleByIdUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.schedulleRepository = mock(SchedulleRepository.class);
        this.getSchedulleByIdUseCase = new GetSchedulleByIdUseCase(this.schedulleRepository);
    }

    @Test
    @DisplayName("Should return schedulle when get schedulle by id")
    void shouldReturnSchedulleWhenGetSchedulleById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Schedulle.class);

        when(this.schedulleRepository.get(anyString())).thenReturn(mockObject);

        var useCaseResponse = this.getSchedulleByIdUseCase.execute(anyString());

        assertNotNull(useCaseResponse);
        assertThat(mockObject.getId(), equalTo(useCaseResponse.getId()));
    }
}
