package com.sicredi.votacao.internal.interactors.associate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.internal.entities.Associate;
import com.sicredi.votacao.internal.repositories.AssociateRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CreateAssociateUseCaseTest {

    private final AssociateRepository associateRepository;
    private final CreateAssociateUseCase createAssociateUseCase;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/associate.json")
    private Resource associateResource;

    @Autowired
    public CreateAssociateUseCaseTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.associateRepository = mock(AssociateRepository.class);
        this.createAssociateUseCase = new CreateAssociateUseCase(this.associateRepository);
    }

    @Test
    @DisplayName("Should return associate when create a new associate")
    void shouldReturnAssociateWhenCreateAssociate() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        var mockObject = objectMapper.readValue(mockResultString, Associate.class);

        when(this.associateRepository.save(any(Associate.class))).thenReturn(mockObject);

        var useCaseResponse = this.createAssociateUseCase.execute(mockObject);

        assertNotNull(useCaseResponse);
        assertThat(mockObject.getId(), equalTo(useCaseResponse.getId()));
    }
}
