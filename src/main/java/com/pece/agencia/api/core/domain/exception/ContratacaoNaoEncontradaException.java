package com.pece.agencia.api.core.domain.exception;

import java.util.UUID;

public class ContratacaoNaoEncontradaException extends Exception {
    private final UUID contratacaoId;

    public ContratacaoNaoEncontradaException(UUID contratacaoId) {
        super(String.format("Contratação com ID %s nao encontrado", contratacaoId));
        this.contratacaoId = contratacaoId;
    }
}
