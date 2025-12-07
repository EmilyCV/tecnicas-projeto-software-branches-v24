package com.pece.agencia.api.core.infrastructure.adapters.acl.hotelaria;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.application.ports.ReservaHospedagemService;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.OfertaHospedagem;
import com.pece.agencia.api.core.domain.model.ReservaHotel;
import com.pece.agencia.api.core.infrastructure.adapters.acl.hotelaria.mapper.ReservaHospedagemMapper;
import com.pece.agencia.api.core.infrastructure.adapters.acl.hotelaria.mapper.ReservaHospedagemRequestMapper;
import com.pece.agencia.api.hotelaria.PlataformaHospedagem;
import com.pece.agencia.api.hotelaria.PlataformaHospedagemOfflineException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ReservaHospedagemServiceAdapter implements ReservaHospedagemService {

    private final ReservaHospedagemMapper reservaHospedagemMapper;
    private final ReservaHospedagemRequestMapper requestMapper;
    private final PlataformaHospedagem hospedagemService;

    public ReservaHotel reservar(OfertaHospedagem hospedagem, Cliente cliente, Periodo periodoViagem) throws PlataformaParceiroOfflineException {
        try {
            PlataformaHospedagem.ReservaHospedagemRequest request = requestMapper.toRequest(hospedagem, cliente, periodoViagem);
            return reservaHospedagemMapper.fromDTO(hospedagemService.reservar(request));
        } catch (PlataformaHospedagemOfflineException ex) {
            throw new PlataformaParceiroOfflineException(format("plataforma-hospedagem-%s", ex.getPlataforma()), ex);
        }
    }

    public String obterIdPlataforma(OfertaHospedagem hospedagem) {
        return hospedagemService.obterIdPlataforma(hospedagem.getId())
                                .valor();
    }
}
