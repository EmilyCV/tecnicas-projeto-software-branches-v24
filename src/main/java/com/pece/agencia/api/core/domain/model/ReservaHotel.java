package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;

import static java.util.Objects.requireNonNull;

@Embeddable
public record ReservaHotel(String numero) {
    public ReservaHotel {
        requireNonNull(numero, "Número da reserva não pode ser nulo");
    }
}
