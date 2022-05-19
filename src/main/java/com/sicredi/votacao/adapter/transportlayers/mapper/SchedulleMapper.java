package com.sicredi.votacao.adapter.transportlayers.mapper;

import com.sicredi.votacao.adapter.transportlayers.openapi.model.ResultOfSchedulleResult;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SchedulleInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SchedulleResult;
import com.sicredi.votacao.internal.entities.ResultOfSchedulle;
import com.sicredi.votacao.internal.entities.Schedulle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SchedulleMapper {

    SchedulleMapper INSTANCE = Mappers.getMapper(SchedulleMapper.class);

    SchedulleResult map(Schedulle source);

    Schedulle map(SchedulleInput source);

    ResultOfSchedulleResult map(ResultOfSchedulle source);
}

