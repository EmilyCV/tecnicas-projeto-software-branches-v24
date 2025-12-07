package com.pece.agencia.api.core.infrastructure.adapters.acl.aereo.mapper;

import com.pece.agencia.api.aereo.internal.infrastructure.adapter.ReservaTransladoAereoService;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.DadosVoo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(
    componentModel = "spring",
    uses = {
            DadosVooMapper.class,
            PassageiroMapper.class
    }
)
public interface ReservaTransladoAereoRequestMapper {
    @Mapping(source = "cliente", target = "passageiro")
    @Mapping(source = "dadosVoo", target = "dadosVoo")
    @Mapping(source = "data", target = "data")
    ReservaTransladoAereoService.ReservaTransladoAereoRequest toRequest(Cliente cliente, DadosVoo dadosVoo, LocalDate data);
}
