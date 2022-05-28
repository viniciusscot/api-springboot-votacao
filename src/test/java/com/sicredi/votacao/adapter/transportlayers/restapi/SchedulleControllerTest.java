package com.sicredi.votacao.adapter.transportlayers.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.ResultOfSchedulle;
import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.interactors.schedulle.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StreamUtils;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SchedulleControllerTest {

    private final MockMvc mockMvc;
    private final CreateSchedulleUseCase createSchedulleUseCase;
    private final DeleteSchedulleByIdUseCase deleteSchedulleByIdUseCase;
    private final GetSchedulleByIdUseCase getSchedulleByIdUseCase;
    private final GetAllSchedullesUseCase getAllSchedullesUseCase;
    private final UpdateSchedulleUseCase updateSchedulleUseCase;
    private final GetResultsOfSchedulleSessionVoteUseCase getResultsOfSchedulleSessionVoteUseCase;
    private final ObjectMapper objectMapper;
    private static final String ANY_ASSOCIATE_ID = "anySchedulleId";

    @Value("classpath:datasource/sicredi/payload/schedulle.json")
    private Resource schedulleResource;

    @Value("classpath:datasource/sicredi/payload/results.json")
    private Resource resultsResource;

    @Autowired
    public SchedulleControllerTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.createSchedulleUseCase = mock(CreateSchedulleUseCase.class);
        this.deleteSchedulleByIdUseCase = mock(DeleteSchedulleByIdUseCase.class);
        this.getSchedulleByIdUseCase = mock(GetSchedulleByIdUseCase.class);
        this.getAllSchedullesUseCase = mock(GetAllSchedullesUseCase.class);
        this.updateSchedulleUseCase = mock(UpdateSchedulleUseCase.class);
        this.getResultsOfSchedulleSessionVoteUseCase = mock(GetResultsOfSchedulleSessionVoteUseCase.class);
        var schedulleController = new SchedulleControllerImpl(this.createSchedulleUseCase, this.deleteSchedulleByIdUseCase,
                this.getSchedulleByIdUseCase, this.getAllSchedullesUseCase, this.updateSchedulleUseCase, this.getResultsOfSchedulleSessionVoteUseCase);
        this.mockMvc = MockMvcBuilders.standaloneSetup(schedulleController).build();
    }

    @Test
    @DisplayName("Should return Schedulle when create schedulle")
    void shouldReturnSchedulleWhenCreateSchedulle() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Schedulle.class);

        when(this.createSchedulleUseCase.execute(any(Schedulle.class))).thenReturn(mockObject);

        this.mockMvc.perform(
                        post("/schedulles")
                                .content(mockResultString)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("any_id")))
                .andExpect(jsonPath("$.name", is("example of name")));
    }

    @Test
    @DisplayName("Should return no content Http 204 when delete schedulle")
    void shouldReturnNoContentHttp204WhenDeleteSchedulle() throws Exception {
        doNothing().when(this.deleteSchedulleByIdUseCase).execute(anyString());

        this.mockMvc.perform(
                        delete("/schedulles/{schedulleId}", ANY_ASSOCIATE_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Should return schedulle when get by id")
    void shouldReturnSchedulleWhenGetById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Schedulle.class);

        when(this.getSchedulleByIdUseCase.execute(anyString())).thenReturn(mockObject);

        this.mockMvc.perform(
                        get("/schedulles/{schedulleId}", ANY_ASSOCIATE_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("any_id")))
                .andExpect(jsonPath("$.name", is("example of name")));

    }

    @Test
    @DisplayName("Should return an schedulle's list when get all")
    void shouldReturnAnSchedulleListWhenGetAll() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Schedulle.class);

        when(this.getAllSchedullesUseCase.execute()).thenReturn(Arrays.asList(mockObject));

        this.mockMvc.perform(
                        get("/schedulles")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is("any_id")))
                .andExpect(jsonPath("$[0].name", is("example of name")));

    }

    @Test
    @DisplayName("Should return a schedulle when update by id")
    void shouldReturnSchedulleWhenUpdateById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.schedulleResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Schedulle.class);

        when(this.updateSchedulleUseCase.execute(anyString(), any(Schedulle.class))).thenReturn(mockObject);

        this.mockMvc.perform(
                        put("/schedulles/{schedulleId}", ANY_ASSOCIATE_ID)
                                .content(mockResultString)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("any_id")))
                .andExpect(jsonPath("$.name", is("example of name")));

    }

    @Test
    @DisplayName("Should return results when get results by schedulle id")
    void shouldReturnResultsWhenGetResultsBySchedulleId() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.resultsResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, ResultOfSchedulle.class);

        when(this.getResultsOfSchedulleSessionVoteUseCase.execute(anyString())).thenReturn(Arrays.asList(mockObject));

        this.mockMvc.perform(
                        get("/schedulles/{schedulleId}/results", ANY_ASSOCIATE_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].session.id", is("628ab683fe50a8279ea076cc")))
                .andExpect(jsonPath("$[0].result", is("EMPATE")));

    }
}
