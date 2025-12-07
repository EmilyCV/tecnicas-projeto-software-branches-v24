package com.pece.agencia.api.core.infrastructure.adapters.acl.aereo.mapper;

import com.pece.agencia.api.aereo.DadosVoo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface DadosVooMapper {
    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "horario", target = "horario")
    DadosVoo toDadosVoo(com.pece.agencia.api.core.domain.model.DadosVoo dadosVoo);

    @InheritInverseConfiguration
    com.pece.agencia.api.core.domain.model.DadosVoo toDadosVoo(DadosVoo dadosVoo);
}