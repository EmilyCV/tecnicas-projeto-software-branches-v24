package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = LocalidadeMapper.class
)
public interface EnderecoMapper {
    @Mapping(source = "cep.numero", target = "cep")
    com.pece.agencia.api.core.controller.v1.dto.Endereco toDTO(Endereco entity);
}
