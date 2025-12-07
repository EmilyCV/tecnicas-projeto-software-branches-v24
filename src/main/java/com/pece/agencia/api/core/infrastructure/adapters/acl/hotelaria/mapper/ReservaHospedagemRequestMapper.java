package com.pece.agencia.api.core.infrastructure.adapters.acl.hotelaria.mapper;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.OfertaHospedagem;
import com.pece.agencia.api.hotelaria.PlataformaHospedagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        HospedeMapper.class
    }
)
public interface ReservaHospedagemRequestMapper {
    @Mapping(source = "hospedagem.id", target = "codigoPromocao")
    @Mapping(source = "cliente", target = "hospede")
    @Mapping(source = "periodoHospedagem", target = "periodo")
    PlataformaHospedagem.ReservaHospedagemRequest toRequest(OfertaHospedagem hospedagem, Cliente cliente, Periodo periodoHospedagem);
}
