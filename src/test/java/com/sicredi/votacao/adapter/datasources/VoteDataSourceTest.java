package com.sicredi.votacao.adapter.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.MongoVoteRepository;
import com.sicredi.votacao.adapter.datasources.services.model.VoteModel;
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
public class VoteDataSourceTest {

    private final MongoVoteRepository mongoVoteRepository;
    private final VoteDataSource voteDataSource;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/vote.json")
    private Resource voteResource;

    @Autowired
    public VoteDataSourceTest(ObjectMapper objectMapper) {
        this.mongoVoteRepository = Mockito.mock(MongoVoteRepository.class);
        this.voteDataSource = new VoteDataSource(this.mongoVoteRepository);
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should return a vote list when get all")
    void shouldReturnVoteListWhenGetAll() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.voteResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, VoteModel.class);

        when(this.mongoVoteRepository.findAll()).thenReturn(Arrays.asList(mockObject));

        var dataSourceResponse = this.voteDataSource.getAll();

        assertNotNull(dataSourceResponse);
        assertThat(Integer.valueOf(1), equalTo(dataSourceResponse.size()));

    }

    @Test
    @DisplayName("Should Return Vote When Save Vote")
    void shouldReturnVoteWhenSave() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.voteResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, VoteModel.class);

        when(this.mongoVoteRepository.save(any())).thenReturn(mockObject);

        var dataSourceResponse = this.voteDataSource.save(any());

        assertNotNull(dataSourceResponse);
        assertThat(mockObject.getId(), equalTo(dataSourceResponse.getId()));

    }

    @Test
    @DisplayName("Should return vote when get by associate id and session id")
    void shouldReturnVoteWhenGetByAssociateIdAndSessionId() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.voteResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, VoteModel.class);

        when(this.mongoVoteRepository
                .findByAssociateIdAndSchedulleId(anyString(), anyString()))
                .thenReturn(Optional.of(mockObject));

        var dataSourceResponse = this.voteDataSource.getByAssociateIdAndSessionId(anyString(), anyString());

        assertNotNull(dataSourceResponse);
        assertThat(mockObject.getId(), equalTo(dataSourceResponse.getId()));
    }

    @Test
    @DisplayName("Should return null when get by associate id and session id")
    void shouldReturnNullWhenGetByAssociateIdAndSessionId() {
        when(this.mongoVoteRepository
                .findByAssociateIdAndSchedulleId(anyString(), anyString()))
                .thenReturn(Optional.empty());

        var dataSourceResponse = this.voteDataSource.getByAssociateIdAndSessionId(anyString(), anyString());

        assertNull(dataSourceResponse);
    }

    @Test
    @DisplayName("Should return a vote's list when get all by session id")
    void shouldReturnAVoteListWhenGetAllBySessionId() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.voteResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, VoteModel.class);

        when(this.mongoVoteRepository.findAllBySessionId(anyString())).thenReturn(Arrays.asList(mockObject));

        var dataSourceResponse = this.voteDataSource.getAllBySessionId(mockObject.getSessionId());

        assertNotNull(dataSourceResponse);
        assertThat(Integer.valueOf(1), equalTo(dataSourceResponse.size()));

    }
}
