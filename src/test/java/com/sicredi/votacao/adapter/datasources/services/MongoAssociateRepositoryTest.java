package com.sicredi.votacao.adapter.datasources.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.model.AssociateModel;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class MongoAssociateRepositoryTest {

    private final MongodExecutable mongodExecutable;
    private final MongoAssociateRepository mongoAssociateRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/associate.json")
    private Resource associateResource;

    @Autowired
    public MongoAssociateRepositoryTest(MongoAssociateRepository mongoAssociateRepository, ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.mongodExecutable = MongoDBTestInitSetup.getMongoDExecutable();
        MongoDBTestInitSetup.getMongoTemplate(this.mongodExecutable, "database-name");
        this.mongoAssociateRepository = mongoAssociateRepository;
    }

    @AfterEach
    void clean() {
        this.mongodExecutable.stop();
    }

    @Test
    @DisplayName("Should return persisted Associate when save")
    void shouldReturnPersistedAssociateWhenSave() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        final var associateModel = objectMapper.readValue(mockResultString, AssociateModel.class);

        final var savedAssociate = this.mongoAssociateRepository.save(associateModel);

        final var optionalRetrievedById = this.mongoAssociateRepository.findById(savedAssociate.getId());
        final var retrievedById = optionalRetrievedById.orElseThrow(IllegalArgumentException::new);

        assertThat(savedAssociate.getId(), equalTo(retrievedById.getId()));
    }

    @Test
    @DisplayName("Should return optional blank when find a not exist register in mongo db")
    void shouldReturnOptionalIsPresentFalseWhenRecordDoNotExistsInMongoDB() {
        final var optionalRetrievedById = this.mongoAssociateRepository.findById("1");
        assertFalse(optionalRetrievedById.isPresent());
    }

}
