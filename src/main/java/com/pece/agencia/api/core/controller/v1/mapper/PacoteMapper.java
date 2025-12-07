package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.model.Pacote;
import com.pece.agencia.api.core.domain.model.TipoDescontoPacote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

import java.time.Period;

@Mapper(
    componentModel = "spring",
    uses = {
        ItemPacoteMapper.class,
        LocalidadeMapper.class,
        ValorMonetarioMapper.class
    }
)
public interface PacoteMapper {
    @Mapping(target = "nome", source = "descricao")
    @Mapping(target = "localidade", source = "destino")
    @Mapping(target = "items", source = "ofertas")
    @Mapping(target = "dataInicio", source = "validade.inicio")
    @Mapping(target = "dataFim", source = "validade.fim")
    @Mapping(target = "desconto", source = "percentualDesconto.valor")
    @Mapping(target = "duracao", source = "duracaoViagem")
    @Mapping(target = "valorDesconto", source = "valorDescontoPromocional")
    @Mapping(target = "preco", source = "precoBase")
    @Mapping(target = "custo", source = "valorTotalAPagar")
    @Mapping(target = "disponibilidade", source = "disponibilidade.valor")
    com.pece.agencia.api.core.controller.v1.dto.Pacote toDTO(Pacote entity);

    @ValueMapping(target = "COMPRA_ANTECIPADA", source = "POR_ANTECIPACAO")
    @ValueMapping(target = "FIXO", source = "FIXO")
    @ValueMapping(target = MappingConstants.NULL, source = MappingConstants.ANY_REMAINING)
    com.pece.agencia.api.core.controller.v1.dto.Pacote.TipoDescontoEnum toDto(TipoDescontoPacote source);

    default int toDto(Period duracaoViagem) {
        return duracaoViagem.getDays();
    }
}
