package com.pece.agencia.api.core.infrastructure.adapters.acl.pagamento.mapper;

import com.pece.agencia.api.core.domain.model.CodigoPagamento;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface CodigoPagamentoMapper {
    CodigoPagamento fromDTO(com.pece.agencia.api.pagamento.CodigoPagamento codigoPagamento);
}
