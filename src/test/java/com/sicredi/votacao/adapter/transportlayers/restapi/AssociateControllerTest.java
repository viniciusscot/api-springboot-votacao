package com.sicredi.votacao.adapter.transportlayers.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Associate;
import com.sicredi.votacao.internal.interactors.associate.*;
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
public class AssociateControllerTest {

    private final MockMvc mockMvc;
    private final CreateAssociateUseCase createAssociateUseCase;
    private final DeleteAssociateByIdUseCase deleteAssociateByIdUseCase;
    private final GetAssociateByIdUseCase getAssociateByIdUseCase;
    private final GetAllAssociatesUseCase getAllAssociatesUseCase;
    private final UpdateAssociateUseCase updateAssociateUseCase;
    private final ObjectMapper objectMapper;
    private static final String ANY_ASSOCIATE_ID = "anyAssociateId";

    @Value("classpath:datasource/sicredi/payload/associate.json")
    private Resource associateResource;

    @Autowired
    public AssociateControllerTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.createAssociateUseCase = mock(CreateAssociateUseCase.class);
        this.deleteAssociateByIdUseCase = mock(DeleteAssociateByIdUseCase.class);
        this.getAssociateByIdUseCase = mock(GetAssociateByIdUseCase.class);
        this.getAllAssociatesUseCase = mock(GetAllAssociatesUseCase.class);
        this.updateAssociateUseCase = mock(UpdateAssociateUseCase.class);
        var associateController = new AssociateControllerImpl(this.createAssociateUseCase, this.deleteAssociateByIdUseCase,
                this.getAssociateByIdUseCase, this.getAllAssociatesUseCase, this.updateAssociateUseCase);
        this.mockMvc = MockMvcBuilders.standaloneSetup(associateController).build();
    }

    @Test
    @DisplayName("Should return Associate when create associate")
    void shouldReturnAssociateWhenCreateAssociate() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Associate.class);

        when(this.createAssociateUseCase.execute(any(Associate.class))).thenReturn(mockObject);

        this.mockMvc.perform(
                        post("/associates")
                                .content(mockResultString)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("any_id")))
                .andExpect(jsonPath("$.cpf", is("01234567890")));
    }

    @Test
    @DisplayName("Should return no content Http 204 when delete associate")
    void shouldReturnNoContentHttp204WhenDeleteAssociate() throws Exception {
        doNothing().when(this.deleteAssociateByIdUseCase).execute(anyString());

        this.mockMvc.perform(
                        delete("/associates/{associateId}", ANY_ASSOCIATE_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Should return associate when get by id")
    void shouldReturnAssociateWhenGetById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Associate.class);

        when(this.getAssociateByIdUseCase.execute(anyString())).thenReturn(mockObject);

        this.mockMvc.perform(
                        get("/associates/{associateId}", ANY_ASSOCIATE_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("any_id")))
                .andExpect(jsonPath("$.cpf", is("01234567890")));

    }

    @Test
    @DisplayName("Should return an associate's list when get all")
    void shouldReturnAnAssociateListWhenGetAll() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Associate.class);

        when(this.getAllAssociatesUseCase.execute()).thenReturn(Arrays.asList(mockObject));

        this.mockMvc.perform(
                        get("/associates")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is("any_id")))
                .andExpect(jsonPath("$[0].cpf", is("01234567890")));

    }

    @Test
    @DisplayName("Should return a associate when update by id")
    void shouldReturnAssociateWhenUpdateById() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Associate.class);

        when(this.updateAssociateUseCase.execute(anyString(), any(Associate.class))).thenReturn(mockObject);

        this.mockMvc.perform(
                        put("/associates/{associateId}", ANY_ASSOCIATE_ID)
                                .content(mockResultString)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("any_id")))
                .andExpect(jsonPath("$.cpf", is("01234567890")));

    }
}
