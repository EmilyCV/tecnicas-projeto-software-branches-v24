package com.pece.agencia.api.hotelaria.internal.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record CodigoPromocao(String valor) {
    public CodigoPromocao {
        Objects.requireNonNull(valor);
    }
}
