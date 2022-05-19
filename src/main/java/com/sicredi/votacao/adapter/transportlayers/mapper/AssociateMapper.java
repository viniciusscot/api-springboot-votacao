package com.sicredi.votacao.adapter.transportlayers.mapper;

import com.sicredi.votacao.adapter.transportlayers.openapi.model.AssociateInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.AssociateResult;
import com.sicredi.votacao.internal.entities.Associate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssociateMapper {

    AssociateMapper INSTANCE = Mappers.getMapper(AssociateMapper.class);

    AssociateResult map(Associate source);

    Associate map(AssociateInput source);

}

