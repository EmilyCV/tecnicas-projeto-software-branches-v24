
package com.pece.agencia.api.core.infrastructure.adapters.acl.aereo.mapper;

import com.pece.agencia.api.aereo.Passageiro;
import com.pece.agencia.api.core.domain.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface PassageiroMapper {
    @Mapping(source = "nome", target = "nome")
    Passageiro toPassageiro(Cliente cliente);

}
