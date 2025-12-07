package com.pece.agencia.api.core.infrastructure.adapters.acl.aereo.mapper;

import com.pece.agencia.api.core.domain.model.ReservaVoo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
            DadosVooMapper.class,
        }
)
public interface ReservaVooMapper {
    @Mapping(source = "eticket", target = "eticket")
    @Mapping(source = "assento.numero", target = "assento.numero")
    @Mapping(source = "horarioEmbarque", target = "horarioEmbarque")
    @Mapping(source = "dadosVoo", target = "dadosVoo")
    ReservaVoo toReservaVoo(com.pece.agencia.api.aereo.ReservaVoo reservaVoo);
}
