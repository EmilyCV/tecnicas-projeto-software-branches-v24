package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.model.Contratacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
            ClienteMapper.class,
            PacoteMapper.class,
            ValorMonetarioMapper.class
        }
)
public interface CompraMapper {
    @Mapping(target = "reservaVooIda", source = "reservaVooIda.eticket")
    @Mapping(target = "assentoVooIda", source = "reservaVooIda.assento.numero")
    @Mapping(target = "embarqueVooIda", source = "reservaVooIda.horarioEmbarque")
    @Mapping(target = "reservaVooVolta", source = "reservaVooVolta.eticket")
    @Mapping(target = "assentoVooVolta", source = "reservaVooVolta.assento.numero")
    @Mapping(target = "embarqueVooVolta", source = "reservaVooVolta.horarioEmbarque")
    @Mapping(target = "pacote", source = "pacoteContratado")
    @Mapping(target = "dataIda", source = "periodoViagem.inicio")
    @Mapping(target = "dataVolta", source = "periodoViagem.fim")
    @Mapping(target = "reservaVeiculo", source = "reservaVeiculo.localizador")
    @Mapping(target = "desconto", source = "valorDesconto")
    @Mapping(target = "dataCompra", source = "momentoCompra")
    @Mapping(target = "stripeChargeId", source = "codigoPagamento.codigo")
    @Mapping(target = "reservaHotel", source = "reservaHotel.numero")
    com.pece.agencia.api.core.controller.v1.dto.Compra toDTO(Contratacao entity);
}
