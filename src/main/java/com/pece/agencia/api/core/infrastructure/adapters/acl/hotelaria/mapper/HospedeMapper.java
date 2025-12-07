package com.pece.agencia.api.core.infrastructure.adapters.acl.hotelaria.mapper;

import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.hotelaria.Hospede;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface HospedeMapper {
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    Hospede toHospede(Cliente cliente);
}
