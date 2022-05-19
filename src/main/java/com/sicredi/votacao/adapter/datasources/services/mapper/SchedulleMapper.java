package com.sicredi.votacao.adapter.datasources.services.mapper;

import com.sicredi.votacao.adapter.datasources.services.model.SchedulleModel;
import com.sicredi.votacao.internal.entities.Schedulle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SchedulleMapper {

    SchedulleMapper INSTANCE = Mappers.getMapper(SchedulleMapper.class);

    SchedulleModel map(Schedulle source);

    Schedulle map(SchedulleModel source);

}

