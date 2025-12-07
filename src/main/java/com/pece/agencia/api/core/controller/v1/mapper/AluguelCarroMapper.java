package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.model.OfertaLocacaoVeiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        TelefoneMapper.class,
        ValorMonetarioMapper.class
    }
)
public interface AluguelCarroMapper {
    @Mapping(source = "parceiroResponsavel.nome", target = "nomeLocadora")
    @Mapping(source = "parceiroResponsavel.emailContato.endereco", target = "email")
    @Mapping(source = "parceiroResponsavel.telefoneContato", target = "telefone")
    @Mapping(source = "categoriaVeiculo", target = "categoriaCarro")
    com.pece.agencia.api.core.controller.v1.dto.AluguelCarro toDTO(OfertaLocacaoVeiculo entity);

}
