package com.sicredi.votacao.adapter.datasources.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.adapter.datasources.services.model.VoteModel;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class MongoVoteRepositoryTest {

    private final MongodExecutable mongodExecutable;
    private final MongoVoteRepository mongoVoteRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:datasource/sicredi/payload/vote.json")
    private Resource associateResource;

    @Autowired
    public MongoVoteRepositoryTest(MongoVoteRepository mongoVoteRepository, ObjectMapper objectMapper) throws IOException {
        this.mongoVoteRepository = mongoVoteRepository;
        this.objectMapper = objectMapper;
        this.mongodExecutable = MongoDBTestInitSetup.getMongoDExecutable();
        MongoDBTestInitSetup.getMongoTemplate(this.mongodExecutable, "database-name");
    }

    @AfterEach
    void clean() {
        this.mongodExecutable.stop();
    }

    @Test
    @DisplayName("Should return persisted Vote when save")
    void shouldReturnPersistedAssociateWhenSave() throws Exception {
        final var mockResultString = StreamUtils.copyToString(this.associateResource.getInputStream(), UTF_8);

        final var voteModel = objectMapper.readValue(mockResultString, VoteModel.class);

        final var savedVote = this.mongoVoteRepository.save(voteModel);

        final Optional<VoteModel> optionalRetrievedById = this.mongoVoteRepository.findById(savedVote.getId());
        final VoteModel retrievedById = optionalRetrievedById.orElseThrow(IllegalArgumentException::new);

        assertThat(savedVote.getId(), equalTo(retrievedById.getId()));
    }

    @Test
    @DisplayName("Should return optional blank when find a not exist register in mongo db")
    void shouldReturnOptionalIsPresentFalseWhenRecordDoNotExistsInMongoDB() {
        final Optional<VoteModel> optionalRetrievedById = this.mongoVoteRepository.findById("1");
        assertFalse(optionalRetrievedById.isPresent());
    }

}
