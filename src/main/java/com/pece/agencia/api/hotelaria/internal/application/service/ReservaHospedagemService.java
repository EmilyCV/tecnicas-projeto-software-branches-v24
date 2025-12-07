package com.pece.agencia.api.hotelaria.internal.application.service;

import com.pece.agencia.api.hotelaria.CodigoHotel;
import com.pece.agencia.api.hotelaria.PlataformaHospedagem;
import com.pece.agencia.api.hotelaria.PlataformaHospedagemOfflineException;
import com.pece.agencia.api.hotelaria.ReservaHospedagem;
import com.pece.agencia.api.hotelaria.internal.application.ports.ReservaHospedagemServiceHandler;
import com.pece.agencia.api.hotelaria.internal.domain.model.OfertaHospedagemPlataformaMapping;
import com.pece.agencia.api.hotelaria.internal.domain.repository.OfertaHospedagemPlataformaMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservaHospedagemService implements PlataformaHospedagem {
    private final OfertaHospedagemPlataformaMappingRepository repository;

    private final List<ReservaHospedagemServiceHandler> handlers;

    public ReservaHospedagem reservar(ReservaHospedagemRequest request) throws PlataformaHospedagemOfflineException {
        OfertaHospedagemPlataformaMapping mapping = repository.findById(request.codigoPromocao()).get();
        for (ReservaHospedagemServiceHandler handler : handlers) {
            if (handler.accepts(mapping)) {
                return handler.reservar(mapping, request);
            }
        }
        throw new IllegalArgumentException("Nenhum handler encontrado para a plataforma: " + mapping.getPlataforma());
    }

    public CodigoHotel obterIdPlataforma(UUID id) {
        return repository.findById(id)
                         .map(OfertaHospedagemPlataformaMapping::getCodigoHotel)
                         .get();
    }
}