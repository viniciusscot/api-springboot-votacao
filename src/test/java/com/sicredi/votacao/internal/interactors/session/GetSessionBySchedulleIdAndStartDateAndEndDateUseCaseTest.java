package com.sicredi.votacao.internal.interactors.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.repositories.SessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.time.OffsetDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GetSessionBySchedulleIdAndStartDateAndEndDateUseCaseTest {

    private final SessionRepository sessionRepository;
    private final GetSessionBySchedulleIdAndStartDateAndEndDateUseCase getSessionBySchedulleIdAndStartDateAndEndDateUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Autowired
    public GetSessionBySchedulleIdAndStartDateAndEndDateUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.sessionRepository = mock(SessionRepository.class);
        this.getSessionBySchedulleIdAndStartDateAndEndDateUseCase = new GetSessionBySchedulleIdAndStartDateAndEndDateUseCase(this.sessionRepository);
    }

    @Test
    @DisplayName("Should return session when get session by schedulle id and start/end date")
    void shouldReturnSessionWhenGetSessionBySchedulleIdAndStartEndDate() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Session.class);

        when(this.sessionRepository.getBySchedulleIdAndStartDateAndEndDate(anyString(), any(OffsetDateTime.class))).thenReturn(mockObject);

        var useCaseResponse = this.getSessionBySchedulleIdAndStartDateAndEndDateUseCase.execute(mockObject.getSchedulleId(), mockObject.getStartDate());

        assertNotNull(useCaseResponse);
        assertThat(mockObject.getId(), equalTo(useCaseResponse.getId()));
    }
}
