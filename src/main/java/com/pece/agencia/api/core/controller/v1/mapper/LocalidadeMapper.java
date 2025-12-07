package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.application.ports.LocacaoVeiculoService;
import com.pece.agencia.api.core.domain.model.Localidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LocalidadeMapper {
    @Autowired
    private LocacaoVeiculoService locacaoVeiculoService;

    @Mapping(source = ".", target = "codigoLocadoraVeiculo")
    public abstract com.pece.agencia.api.core.controller.v1.dto.Localidade toDTO(Localidade entity);

    protected String toCodigoPlataformaLocacaoVeiculo(Localidade localidade) {
        return locacaoVeiculoService.obterCodigoPlataformaLocacaoVeiculo(localidade);
    }
}