package com.sicredi.votacao.adapter.transportlayers.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Vote;
import com.sicredi.votacao.internal.interactors.votes.CreateVoteUseCase;
import com.sicredi.votacao.internal.interactors.votes.GetAllVotesUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {
    private final GetAllVotesUseCase getAllVotesUseCase;
    private final CreateVoteUseCase createVoteUseCase;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/vote.json")
    private Resource voteResource;

    @Autowired
    public VoteControllerTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.getAllVotesUseCase = Mockito.mock(GetAllVotesUseCase.class);
        this.createVoteUseCase = Mockito.mock(CreateVoteUseCase.class);
        var sessionController = new VoteControllerImpl(this.getAllVotesUseCase, this.createVoteUseCase);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sessionController).build();
    }

    @Test
    @DisplayName("Should return vote when create vote")
    void shouldReturnVoteWhenCreateVote() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.voteResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Vote.class);

        when(this.createVoteUseCase.execute(any(Vote.class))).thenReturn(mockObject);

        this.mockMvc.perform(
                        post("/votes")
                                .content(mockResultString)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.decision", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.associateId", is("any_associate_id")))
                .andExpect(jsonPath("$.schedulleId", is("any_schedulle_id")));
    }

    @Test
    @DisplayName("Should return a vote's list when get all votes")
    void shouldReturnAVoteListWhenGetAllVotes() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.voteResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Vote.class);

        when(this.getAllVotesUseCase.execute()).thenReturn(Arrays.asList(mockObject));

        this.mockMvc.perform(
                        get("/votes")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].decision", is(Boolean.TRUE)))
                .andExpect(jsonPath("$[0].associateId", is("any_associate_id")))
                .andExpect(jsonPath("$[0].schedulleId", is("any_schedulle_id")));
    }
}
