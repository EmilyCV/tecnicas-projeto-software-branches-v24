package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record CodigoPagamento(String codigo) {
    public CodigoPagamento {
        Objects.requireNonNull(codigo);
    }
}
