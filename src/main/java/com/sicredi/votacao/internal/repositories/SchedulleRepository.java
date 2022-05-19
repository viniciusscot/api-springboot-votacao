package com.sicredi.votacao.internal.repositories;

import com.sicredi.votacao.internal.entities.Schedulle;

import java.util.List;

public interface SchedulleRepository {

    Schedulle save(final Schedulle schedulle);

    void delete(final String schedulleId);

    Schedulle get(final String schedulleId);

    List<Schedulle> getAll();
}
