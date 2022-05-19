package com.sicredi.votacao.adapter.datasources;

import com.sicredi.votacao.adapter.datasources.services.MongoVoteRepository;
import com.sicredi.votacao.adapter.datasources.services.mapper.VoteMapper;
import com.sicredi.votacao.internal.entities.Vote;
import com.sicredi.votacao.internal.repositories.VoteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteDataSource implements VoteRepository {

    private final MongoVoteRepository repository;

    public VoteDataSource(MongoVoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Vote> getAll() {
        return this.repository.findAll().stream()
                .map(a -> VoteMapper.INSTANCE.map(a))
                .collect(Collectors.toList());
    }

    @Override
    public Vote save(final Vote vote) {
        final var voteModel = VoteMapper.INSTANCE.map(vote);
        final var voteSaved = this.repository.save(voteModel);
        return VoteMapper.INSTANCE.map(voteSaved);
    }

    @Override
    public Vote getByAssociateIdAndSessionId(final String associateId, final String schedulleId) {
        final var vote = this.repository.findByAssociateIdAndSchedulleId(associateId, schedulleId);

        if (vote.isEmpty())
            return null;

        return VoteMapper.INSTANCE.map(vote.get());
    }

    @Override
    public List<Vote> getAllBySessionId(String sessionId) {
        return this.repository.findAllBySessionId(sessionId).stream()
                .map(v -> VoteMapper.INSTANCE.map(v))
                .collect(Collectors.toList());
    }
}
