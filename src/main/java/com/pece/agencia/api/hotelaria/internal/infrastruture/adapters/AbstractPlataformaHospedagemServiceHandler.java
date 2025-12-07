package com.pece.agencia.api.hotelaria.internal.infrastruture.adapters;

import com.pece.agencia.api.common.archunit.MayParseExceptionMessage;
import com.pece.agencia.api.hotelaria.Plataforma;
import com.pece.agencia.api.hotelaria.PlataformaHospedagemOfflineException;
import com.pece.agencia.api.hotelaria.ReservaHospedagem;
import com.pece.agencia.api.hotelaria.internal.application.service.ReservaHospedagemService;
import com.pece.agencia.api.hotelaria.internal.application.ports.ReservaHospedagemServiceHandler;
import com.pece.agencia.api.hotelaria.internal.domain.model.OfertaHospedagemPlataformaMapping;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPlataformaHospedagemServiceHandler implements ReservaHospedagemServiceHandler {
    private final Plataforma plataforma;

    protected abstract ReservaHospedagem doReservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request);

    @Override
    public boolean accepts(OfertaHospedagemPlataformaMapping mapping) {
        return mapping.getPlataforma() == this.plataforma;
    }

    @Override
    @MayParseExceptionMessage
    public ReservaHospedagem reservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request) throws PlataformaHospedagemOfflineException {
        try {
            return doReservar(mapping, request);
        } catch (Exception ex) {
            if (ex.getMessage().contains("I/O error on POST request for")) {
                throw new PlataformaHospedagemOfflineException(this.plataforma, ex);
            } else {
                throw new RuntimeException("Erro generico ao chamar api", ex);
            }
        }
    }
}
