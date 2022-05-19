package com.sicredi.votacao.adapter.datasources.services;

import com.sicredi.votacao.adapter.datasources.services.model.AssociateModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAssociateRepository extends MongoRepository<AssociateModel, String> {
}
