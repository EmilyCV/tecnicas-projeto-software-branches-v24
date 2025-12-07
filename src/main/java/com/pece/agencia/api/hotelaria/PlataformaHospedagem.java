package com.pece.agencia.api.hotelaria;

import com.pece.agencia.api.common.Periodo;

import java.util.UUID;

public interface PlataformaHospedagem {
    ReservaHospedagem reservar(ReservaHospedagemRequest request) throws PlataformaHospedagemOfflineException;
    CodigoHotel obterIdPlataforma(UUID codigoPromocao);

    record ReservaHospedagemRequest(UUID codigoPromocao, Hospede hospede, Periodo periodo) {
    }
}
