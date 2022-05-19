package com.sicredi.votacao.adapter.datasources.services.mapper;

import com.sicredi.votacao.adapter.datasources.services.model.AssociateModel;
import com.sicredi.votacao.internal.entities.Associate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssociateMapper {

    AssociateMapper INSTANCE = Mappers.getMapper(AssociateMapper.class);

    AssociateModel map(Associate source);

    Associate map(AssociateModel source);

}

