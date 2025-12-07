package com.pece.agencia.api.hotelaria.internal.application.ports;

import com.pece.agencia.api.hotelaria.PlataformaHospedagemOfflineException;
import com.pece.agencia.api.hotelaria.ReservaHospedagem;
import com.pece.agencia.api.hotelaria.internal.application.service.ReservaHospedagemService;
import com.pece.agencia.api.hotelaria.internal.domain.model.OfertaHospedagemPlataformaMapping;

public interface ReservaHospedagemServiceHandler {
    boolean accepts(OfertaHospedagemPlataformaMapping mapping);
    ReservaHospedagem reservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request) throws PlataformaHospedagemOfflineException;
}
