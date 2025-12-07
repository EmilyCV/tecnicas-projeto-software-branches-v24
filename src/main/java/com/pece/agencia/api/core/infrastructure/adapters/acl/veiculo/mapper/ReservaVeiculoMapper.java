package com.pece.agencia.api.core.infrastructure.adapters.acl.veiculo.mapper;

import com.pece.agencia.api.core.domain.model.ReservaVeiculo;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface ReservaVeiculoMapper {
    ReservaVeiculo fromDTO(com.pece.agencia.api.veiculo.ReservaVeiculo reservaVeiculo);
}
