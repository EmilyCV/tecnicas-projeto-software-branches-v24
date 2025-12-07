package com.pece.agencia.api.core.domain.exception;

import java.util.UUID;

public class LocalidadeNaoEncontradaException extends Exception {
    private final UUID localidadeId;

    public LocalidadeNaoEncontradaException(UUID localidadeId) {
        super(String.format("Localidade com ID %s nao encontrado", localidadeId));
        this.localidadeId = localidadeId;
    }
}
