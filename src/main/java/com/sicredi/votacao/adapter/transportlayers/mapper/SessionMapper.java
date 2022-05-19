package com.sicredi.votacao.adapter.transportlayers.mapper;

import com.sicredi.votacao.adapter.transportlayers.openapi.model.ResultOfSchedulleResult;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SessionInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SessionResult;
import com.sicredi.votacao.internal.entities.ResultOfSchedulle;
import com.sicredi.votacao.internal.entities.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    SessionResult map(Session source);

    Session map(SessionInput source);
}

