package com.sicredi.votacao.internal.repositories;

import com.sicredi.votacao.internal.entities.Associate;

import java.util.List;

public interface AssociateRepository {

    Associate save(final Associate associate);

    void delete(final String associateId);

    Associate get(final String associateId);

    List<Associate> getAll();
}
