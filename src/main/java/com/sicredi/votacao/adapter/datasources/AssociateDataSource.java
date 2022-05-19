package com.sicredi.votacao.adapter.datasources;

import com.sicredi.votacao.adapter.datasources.services.MongoAssociateRepository;
import com.sicredi.votacao.adapter.datasources.services.mapper.AssociateMapper;
import com.sicredi.votacao.bootstrap.exceptions.AssociateNotFoundException;
import com.sicredi.votacao.bootstrap.exceptions.EntityInUseException;
import com.sicredi.votacao.internal.entities.Associate;
import com.sicredi.votacao.internal.repositories.AssociateRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssociateDataSource implements AssociateRepository {

    private static final String MSG_ASSOCIATE_IN_USE
            = "Code associate %d cannot be removed as it is in use";

    private final MongoAssociateRepository repository;

    public AssociateDataSource(MongoAssociateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Associate save(final Associate associate) {
        final var associateModel = AssociateMapper.INSTANCE.map(associate);
        final var associateSaved = this.repository.save(associateModel);
        return AssociateMapper.INSTANCE.map(associateSaved);
    }

    @Override
    public void delete(final String associateId) {
        try {
            this.repository.deleteById(associateId);
        } catch (EmptyResultDataAccessException e) {
            throw new AssociateNotFoundException(associateId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ASSOCIATE_IN_USE, associateId));
        }
    }

    @Override
    public Associate get(final String associateId) {
        return this.repository.findById(associateId)
                .map(a -> AssociateMapper.INSTANCE.map(a))
                .orElseThrow(() -> new AssociateNotFoundException(associateId));
    }

    @Override
    public List<Associate> getAll() {
        return this.repository.findAll().stream()
                .map(a -> AssociateMapper.INSTANCE.map(a))
                .collect(Collectors.toList());
    }
}
