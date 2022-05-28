package com.sicredi.votacao.internal.interactors.schedulle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.repositories.SchedulleRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GetAllSchedulesUseCaseTest {

    private final SchedulleRepository schedulleRepository;
    private final GetAllSchedullesUseCase getAllSchedullesUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/schedulle.json")
    private Resource schedulleResource;

    @Autowired
    public GetAllSchedulesUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.schedulleRepository = mock(SchedulleRepository.class);
        this.getAllSchedullesUseCase = new GetAllSchedullesUseCase(this.schedulleRepository);
    }

    @Test
    @DisplayName("Should return schedulle's list when get all")
    void shouldReturnSchedulleListWhenGetAll() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Schedulle.class);

        when(this.schedulleRepository.getAll()).thenReturn(Arrays.asList(mockObject));

        var useCaseResponse = this.getAllSchedullesUseCase.execute();

        assertNotNull(useCaseResponse);
        assertThat(Integer.valueOf(1), CoreMatchers.equalTo(useCaseResponse.size()));
    }
}
