package com.pece.agencia.api.pagamento;


import com.pece.agencia.api.common.DadosCartao;

import javax.money.MonetaryAmount;

public interface GatewayPagamento {
    CodigoPagamento pagar(DadosCartao dadosCartao, MonetaryAmount value) throws FalhaCobrancaException, FalhaConectividadeException;
}
