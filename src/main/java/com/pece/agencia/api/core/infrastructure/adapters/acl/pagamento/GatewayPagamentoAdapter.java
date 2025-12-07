package com.pece.agencia.api.core.infrastructure.adapters.acl.pagamento;

import com.pece.agencia.api.common.DadosCartao;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.domain.model.CodigoPagamento;
import com.pece.agencia.api.core.infrastructure.adapters.acl.pagamento.mapper.CodigoPagamentoMapper;
import com.pece.agencia.api.pagamento.FalhaCobrancaException;
import com.pece.agencia.api.pagamento.FalhaConectividadeException;
import com.pece.agencia.api.pagamento.GatewayPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;

@Service
@RequiredArgsConstructor
public class GatewayPagamentoAdapter implements com.pece.agencia.api.core.application.ports.GatewayPagamento {
    private final GatewayPagamento service;
    private final CodigoPagamentoMapper codigoPagamentoMapper;

    public CodigoPagamento pagar(DadosCartao dadosCartao, MonetaryAmount value) throws com.pece.agencia.api.core.domain.exception.FalhaCobrancaException, PlataformaParceiroOfflineException {
        try {
            return codigoPagamentoMapper.fromDTO(service.pagar(dadosCartao, value));
        } catch (FalhaCobrancaException ex) {
            throw new com.pece.agencia.api.core.domain.exception.FalhaCobrancaException("Falha ao processar o pagamento", ex);
        } catch (FalhaConectividadeException ex) {
            throw new PlataformaParceiroOfflineException("stripe", ex);
        }
    }
}
