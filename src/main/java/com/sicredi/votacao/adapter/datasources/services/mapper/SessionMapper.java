package com.sicredi.votacao.adapter.datasources.services.mapper;

import com.sicredi.votacao.adapter.datasources.services.event.SessionEvent;
import com.sicredi.votacao.adapter.datasources.services.model.SessionModel;
import com.sicredi.votacao.internal.entities.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    SessionModel map(Session source);

    Session map(SessionModel source);

    SessionEvent mapEvent(Session source);

}

