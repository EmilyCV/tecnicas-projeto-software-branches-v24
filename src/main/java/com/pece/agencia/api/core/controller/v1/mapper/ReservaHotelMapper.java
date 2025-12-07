package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.application.ports.ReservaHospedagemService;
import com.pece.agencia.api.core.domain.model.OfertaHospedagem;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {
                EnderecoMapper.class,
                TelefoneMapper.class,
                ValorMonetarioMapper.class
        }
)
@RequiredArgsConstructor
public abstract class ReservaHotelMapper {
    @Autowired
    private ReservaHospedagemService reservaHospedagemService;

    @Mapping(source = "parceiroResponsavel.nome", target = "nomeHotel")
    @Mapping(source = "parceiroResponsavel.endereco", target = "endereco")
    @Mapping(source = "parceiroResponsavel.emailContato.endereco", target = "email")
    @Mapping(source = "parceiroResponsavel.telefoneContato", target = "telefone")
    @Mapping(source = "ofertaHospedagem", target = "idPlataforma")

    public abstract com.pece.agencia.api.core.controller.v1.dto.ReservaHotel toDTO(OfertaHospedagem ofertaHospedagem);

    protected String idPlataforma(OfertaHospedagem ofertaHospedagem) {
        return reservaHospedagemService.obterIdPlataforma(ofertaHospedagem);
    }

    @AfterMapping
    public void processEnderecoId(@MappingTarget com.pece.agencia.api.core.controller.v1.dto.ReservaHotel dto) {
        dto.getEndereco().setId(dto.getId());
    }
}
