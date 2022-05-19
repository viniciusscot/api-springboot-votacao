package com.sicredi.votacao.adapter.transportlayers.mapper;

import com.sicredi.votacao.adapter.transportlayers.openapi.model.VoteInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.VoteResult;
import com.sicredi.votacao.internal.entities.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoteMapper {

    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    VoteResult map(Vote source);

    Vote map(VoteInput source);

}

