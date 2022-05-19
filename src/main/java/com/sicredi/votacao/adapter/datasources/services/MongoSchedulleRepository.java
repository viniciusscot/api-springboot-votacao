package com.sicredi.votacao.adapter.datasources.services;

import com.sicredi.votacao.adapter.datasources.services.model.SchedulleModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoSchedulleRepository extends MongoRepository<SchedulleModel, String> {
}
