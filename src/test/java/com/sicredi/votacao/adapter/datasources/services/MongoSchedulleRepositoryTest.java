package com.sicredi.votacao.adapter.datasources.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.model.SchedulleModel;
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
public class MongoSchedulleRepositoryTest {

    private final MongodExecutable mongodExecutable;
    private final MongoSchedulleRepository mongoSchedulleRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/schedulle.json")
    private Resource associateResource;

    @Autowired
    public MongoSchedulleRepositoryTest(MongoSchedulleRepository mongoSchedulleRepository, ObjectMapper objectMapper) throws IOException {
        this.mongoSchedulleRepository = mongoSchedulleRepository;
        this.objectMapper = objectMapper;
        this.mongodExecutable = MongoDBTestInitSetup.getMongoDExecutable();
        MongoDBTestInitSetup.getMongoTemplate(this.mongodExecutable, "database-name");
    }

    @AfterEach
    void clean() {
        this.mongodExecutable.stop();
    }

    @Test
    @DisplayName("Should return persisted Schedulle when save")
    void shouldReturnPersistedAssociateWhenSave() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        final var schedulleModel = objectMapper.readValue(mockResultString, SchedulleModel.class);

        final var savedSchedulle = this.mongoSchedulleRepository.save(schedulleModel);

        final var optionalRetrievedById = this.mongoSchedulleRepository.findById(savedSchedulle.getId());
        final var retrievedById = optionalRetrievedById.orElseThrow(IllegalArgumentException::new);

        assertThat(savedSchedulle.getId(), equalTo(retrievedById.getId()));
    }

    @Test
    @DisplayName("Should return optional blank when find a not exist register in mongo db")
    void shouldReturnOptionalIsPresentFalseWhenRecordDoNotExistsInMongoDB() {
        final var optionalRetrievedById = this.mongoSchedulleRepository.findById("1");
        assertFalse(optionalRetrievedById.isPresent());
    }

}
