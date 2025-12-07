package com.pece.agencia.api.core.infrastructure.adapters.acl.hotelaria.mapper;

import com.pece.agencia.api.core.domain.model.ReservaHotel;
import com.pece.agencia.api.hotelaria.ReservaHospedagem;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring"
)
public interface ReservaHospedagemMapper {
    ReservaHotel fromDTO(ReservaHospedagem reserva);
}
