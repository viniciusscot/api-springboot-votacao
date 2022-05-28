package com.sicredi.votacao.adapter.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.MongoSchedulleRepository;
import com.sicredi.votacao.adapter.datasources.services.model.SchedulleModel;
import com.sicredi.votacao.bootstrap.exceptions.SchedulleNotFoundException;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SchedulleDataSourceTest {

    private final MongoSchedulleRepository mongoSchedulleRepository;
    private final SchedulleDataSource schedulleDataSource;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/schedulle.json")
    private Resource schedulleResource;

    @Autowired
    public SchedulleDataSourceTest(ObjectMapper objectMapper) {
        this.mongoSchedulleRepository = Mockito.mock(MongoSchedulleRepository.class);
        this.schedulleDataSource = new SchedulleDataSource(this.mongoSchedulleRepository);
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Should Return Schedulle When Save Schedulle")
    void shouldReturnSchedulleWhenSave() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SchedulleModel.class);

        when(this.mongoSchedulleRepository.save(any())).thenReturn(mockObject);

        var dataSourceResponse = this.schedulleDataSource.save(any());

        assertNotNull(dataSourceResponse);
        assertThat(mockObject.getId(), equalTo(dataSourceResponse.getId()));

    }

    @Test
    @DisplayName("Should return void when delete schedulle")
    void shouldReturnVoidWhenDeleteSchedulle() {
        doNothing().when(this.mongoSchedulleRepository).deleteById(anyString());

        assertDoesNotThrow(() -> this.schedulleDataSource.delete(anyString()));

    }

    @Test
    @DisplayName("Should return schedulle when get by id")
    void shouldReturnSchedulleWhenGetById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SchedulleModel.class);

        when(this.mongoSchedulleRepository.findById(anyString())).thenReturn(Optional.of(mockObject));

        var dataSourceResponse = this.schedulleDataSource.get(mockObject.getId());

        assertNotNull(dataSourceResponse);
        assertThat(mockObject.getId(), equalTo(dataSourceResponse.getId()));
    }

    @Test
    @DisplayName("Should throw exception when get by non-existing if")
    void shouldThrowExceptionWhenGetByNonExistingId() {
        when(this.mongoSchedulleRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(SchedulleNotFoundException.class, () -> this.schedulleDataSource.get(anyString()));
    }

    @Test
    @DisplayName("Should return a schedulle list when get all")
    void shouldReturnSchedulleListWhenGetAll() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, SchedulleModel.class);

        when(this.mongoSchedulleRepository.findAll()).thenReturn(Arrays.asList(mockObject));

        var dataSourceResponse = this.schedulleDataSource.getAll();

        assertNotNull(dataSourceResponse);
        assertThat(Integer.valueOf(1), equalTo(dataSourceResponse.size()));

    }
}
