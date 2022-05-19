package com.sicredi.votacao.adapter.datasources.services.mapper;

import com.sicredi.votacao.adapter.datasources.services.data.response.VerifyCpfResponse;
import com.sicredi.votacao.internal.entities.VerifyCpf;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VerificaCpfMapper {

    VerificaCpfMapper INSTANCE = Mappers.getMapper(VerificaCpfMapper.class);

    VerifyCpf map(VerifyCpfResponse source);
}
