package com.sicredi.votacao.internal.interactors.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.bootstrap.exceptions.EntityInUseException;
import com.sicredi.votacao.bootstrap.utils.DateUtils;
import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.entities.Session;
import com.sicredi.votacao.internal.interactors.schedulle.GetSchedulleByIdUseCase;
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

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OpenVoteSessionUseCaseTest {

    private final SessionRepository sessionRepository;
    private final GetSchedulleByIdUseCase getSchedulleByIdUseCase;
    private final GetSessionBySchedulleIdAndStartDateAndEndDateUseCase getSessionBySchedulleIdAndStartDateAndEndDateUseCase;
    private final DateUtils dateUtils;
    private final OpenVoteSessionUseCase openVoteSessionUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Value("classpath:datasource/sicredi/payload/schedulle.json")
    private Resource schedulleResource;

    @Autowired
    public OpenVoteSessionUseCaseTest(ObjectMapper objectMapper) {
        this.getSchedulleByIdUseCase = mock(GetSchedulleByIdUseCase.class);
        this.getSessionBySchedulleIdAndStartDateAndEndDateUseCase = mock(GetSessionBySchedulleIdAndStartDateAndEndDateUseCase.class);
        this.dateUtils = mock(DateUtils.class);
        this.objectMapper = objectMapper;
        this.sessionRepository = mock(SessionRepository.class);
        this.openVoteSessionUseCase = new OpenVoteSessionUseCase(this.sessionRepository, this.getSchedulleByIdUseCase,
                this.getSessionBySchedulleIdAndStartDateAndEndDateUseCase, this.dateUtils);
    }

    @Test
    @DisplayName("Should return session when open vote session")
    void shouldReturnSessionWhenOpenVoteSession() throws Exception {
        final var mockSessionString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);
        var mockSession = objectMapper.readValue(mockSessionString, Session.class);
        final var mockSchedulleString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);
        var mockSchedulle = objectMapper.readValue(mockSchedulleString, Schedulle.class);
        final var time = Calendar.getInstance().getTime();

        when(this.dateUtils.getDate()).thenReturn(time);
        when(this.getSchedulleByIdUseCase.execute(anyString())).thenReturn(mockSchedulle);
        when(this.dateUtils.addMinutesToDate(any(Date.class), any(BigInteger.class))).thenReturn(time);
        when(this.sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        var useCaseResponse = this.openVoteSessionUseCase.execute(mockSession);

        assertNotNull(useCaseResponse);
        assertThat(mockSession.getId(), equalTo(useCaseResponse.getId()));
    }

    @Test
    @DisplayName("Should throw exception when open vote session")
    void shouldThrowExceptionWhenOpenVoteSession() throws Exception {
        final var mockSessionString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);
        var mockSession = objectMapper.readValue(mockSessionString, Session.class);
        final var mockSchedulleString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);
        var mockSchedulle = objectMapper.readValue(mockSchedulleString, Schedulle.class);
        final var time = Calendar.getInstance().getTime();

        when(this.dateUtils.getDate()).thenReturn(time);
        when(this.getSchedulleByIdUseCase.execute(anyString())).thenReturn(mockSchedulle);
        when(this.dateUtils.addMinutesToDate(any(Date.class), any(BigInteger.class))).thenReturn(time);
        when(this.getSessionBySchedulleIdAndStartDateAndEndDateUseCase.execute(anyString(), any(OffsetDateTime.class))).thenReturn(mockSession);

        assertThrows(EntityInUseException.class, () -> this.openVoteSessionUseCase.execute(mockSession));
    }
}
