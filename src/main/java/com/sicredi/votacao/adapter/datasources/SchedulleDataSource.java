package com.sicredi.votacao.adapter.datasources;

import com.sicredi.votacao.adapter.datasources.services.MongoSchedulleRepository;
import com.sicredi.votacao.adapter.datasources.services.mapper.SchedulleMapper;
import com.sicredi.votacao.bootstrap.exceptions.EntityInUseException;
import com.sicredi.votacao.bootstrap.exceptions.SchedulleNotFoundException;
import com.sicredi.votacao.internal.entities.Schedulle;
import com.sicredi.votacao.internal.repositories.SchedulleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SchedulleDataSource implements SchedulleRepository {

    private static final String MSG_SCHEDULLE_IN_USE
            = "Code schedulle %d cannot be removed as it is in use";

    private final MongoSchedulleRepository repository;

    public SchedulleDataSource(MongoSchedulleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Schedulle save(final Schedulle schedulle) {
        final var schedulleModel = SchedulleMapper.INSTANCE.map(schedulle);
        final var schedulleSaved = this.repository.save(schedulleModel);
        return SchedulleMapper.INSTANCE.map(schedulleSaved);
    }

    @Override
    public void delete(final String schedulleId) {
        this.repository.deleteById(schedulleId);
    }

    @Override
    public Schedulle get(final String schedulleId) {
        return this.repository.findById(schedulleId)
                .map(a -> SchedulleMapper.INSTANCE.map(a))
                .orElseThrow(() -> new SchedulleNotFoundException(schedulleId));
    }

    @Override
    public List<Schedulle> getAll() {
        return this.repository.findAll().stream()
                .map(a -> SchedulleMapper.INSTANCE.map(a))
                .collect(Collectors.toList());
    }
}
