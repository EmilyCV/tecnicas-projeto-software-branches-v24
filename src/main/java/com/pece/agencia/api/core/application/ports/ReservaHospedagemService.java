package com.pece.agencia.api.core.application.ports;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.OfertaHospedagem;
import com.pece.agencia.api.core.domain.model.ReservaHotel;

public interface ReservaHospedagemService {

    ReservaHotel reservar(OfertaHospedagem hospedagem, Cliente cliente, Periodo periodoViagem) throws PlataformaParceiroOfflineException;
    String obterIdPlataforma(OfertaHospedagem hospedagem);
}
