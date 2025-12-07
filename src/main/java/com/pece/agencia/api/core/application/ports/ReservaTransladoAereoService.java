package com.pece.agencia.api.core.application.ports;

import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.DadosVoo;
import com.pece.agencia.api.core.domain.model.ReservaVoo;

import java.time.LocalDate;

public interface ReservaTransladoAereoService {
    ReservaVoo reservar(Cliente cliente, DadosVoo dadosVoo, LocalDate data) throws PlataformaParceiroOfflineException;
}
