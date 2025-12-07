package com.pece.agencia.api.core.infrastructure.adapters.acl.veiculo.mapper;

import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.veiculo.Motorista;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface MotoristaMapper {
    @Mapping(source = "nome", target = "nome")
    Motorista toMotorista(Cliente cliente);
}
