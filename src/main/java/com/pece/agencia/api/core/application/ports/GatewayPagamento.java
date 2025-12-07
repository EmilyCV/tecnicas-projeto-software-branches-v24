package com.pece.agencia.api.core.application.ports;

import com.pece.agencia.api.common.DadosCartao;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.domain.exception.FalhaCobrancaException;
import com.pece.agencia.api.core.domain.model.CodigoPagamento;

import javax.money.MonetaryAmount;

public interface GatewayPagamento {
    CodigoPagamento pagar(DadosCartao dadosCartao, MonetaryAmount value) throws FalhaCobrancaException, PlataformaParceiroOfflineException;
}