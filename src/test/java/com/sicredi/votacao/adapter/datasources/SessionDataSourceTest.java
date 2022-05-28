package com.sicredi.votacao.adapter.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.MongoSessionRepository;
import com.sicredi.votacao.adapter.datasources.services.model.SessionModel;
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

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SessionDataSourceTest {

    private final MongoSessionRepository mongoSessionRepository;
    private final SessionDataSource sessionDataSource;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/session.json")
    private Resource sessionResource;

    @Autowired
    public SessionDataSourceTest(ObjectMapper objectMapper) {
        this.mongoSessionRepository = Mockito.mock(MongoSessionRepository.class);
        this.sessionDataSource = new SessionDataSource(this.mongoSessionRepository);
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should Return Session When Save Session")
    void shouldReturnSessionWhenSave() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SessionModel.class);

        when(this.mongoSessionRepository.save(any())).thenReturn(mockObject);

        var dataSourceResponse = this.sessionDataSource.save(any());

        assertNotNull(dataSourceResponse);
        assertThat(mockObject.getId(), equalTo(dataSourceResponse.getId()));

    }

    @Test
    @DisplayName("Should return session when get by schedulle id and start/end date")
    void shouldReturnSessionWhenGetBySchedulleIdAndStartDateAndEndDate() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SessionModel.class);

        when(this.mongoSessionRepository
                .findBySchedulleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(anyString(), any(OffsetDateTime.class), any(OffsetDateTime.class)))
                .thenReturn(Optional.of(mockObject));

        var dataSourceResponse = this.sessionDataSource.getBySchedulleIdAndStartDateAndEndDate(mockObject.getSchedulleId(), mockObject.getStartDate());

        assertNotNull(dataSourceResponse);
        assertThat(mockObject.getId(), equalTo(dataSourceResponse.getId()));

    }

    @Test
    @DisplayName("Should return null when get by schedulle id and start/end date")
    void shouldReturnNullWhenGetBySchedulleIdAndStartDateAndEndDate() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SessionModel.class);

        when(this.mongoSessionRepository
                .findBySchedulleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(anyString(), any(OffsetDateTime.class), any(OffsetDateTime.class)))
                .thenReturn(Optional.empty());

        var dataSourceResponse = this.sessionDataSource.getBySchedulleIdAndStartDateAndEndDate(mockObject.getSchedulleId(), mockObject.getStartDate());

        assertNull(dataSourceResponse);
    }

    @Test
    @DisplayName("Should return a session's list when get all by schedulle id")
    void shouldReturnASessionListWhenGetAllBySchedulleId() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.sessionResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SessionModel.class);

        when(this.mongoSessionRepository.findAllBySchedulleId(anyString())).thenReturn(Arrays.asList(mockObject));

        var dataSourceResponse = this.sessionDataSource.getAllBySchedulleId(mockObject.getSchedulleId());

        assertNotNull(dataSourceResponse);
        assertThat(Integer.valueOf(1), equalTo(dataSourceResponse.size()));
    }


}
