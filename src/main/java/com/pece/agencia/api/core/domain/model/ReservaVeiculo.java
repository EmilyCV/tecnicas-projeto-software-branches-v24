package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;

import static java.util.Objects.requireNonNull;

@Embeddable
public record ReservaVeiculo(String localizador) {
    public ReservaVeiculo {
        requireNonNull(localizador, "Lccalizador não pode ser nulo");
    }
}
