package com.pece.agencia.api.core.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PacoteNaoEncontradoException extends Exception {
    private final UUID pacoteId;

    public PacoteNaoEncontradoException(UUID pacoteId) {
        super(String.format("Pacote com ID %s nao encontrado", pacoteId));
        this.pacoteId = pacoteId;
    }
}
