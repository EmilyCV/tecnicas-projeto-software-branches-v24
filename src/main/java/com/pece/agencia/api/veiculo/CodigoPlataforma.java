package com.pece.agencia.api.veiculo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record CodigoPlataforma(String codigo) {
    public CodigoPlataforma {
        Objects.requireNonNull(codigo);
    }
}
