package com.pece.agencia.api.core.application.ports;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.Localidade;
import com.pece.agencia.api.core.domain.model.OfertaLocacaoVeiculo;
import com.pece.agencia.api.core.domain.model.ReservaVeiculo;

import java.util.UUID;

public interface LocacaoVeiculoService {

    ReservaVeiculo locar(OfertaLocacaoVeiculo ofertaLocacaoVeiculo, Cliente motorista, Localidade destino, Periodo periodoViagem) throws PlataformaParceiroOfflineException;
    default String obterCodigoPlataformaLocacaoVeiculo(Localidade destino) {
       return obterCodigoPlataformaLocacaoVeiculo(destino.getId());
    }
    String obterCodigoPlataformaLocacaoVeiculo(UUID destino);
}
