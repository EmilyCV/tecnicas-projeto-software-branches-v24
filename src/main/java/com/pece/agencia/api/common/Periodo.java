package com.pece.agencia.api.common;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public record Periodo(LocalDate inicio, LocalDate fim) {
    public Periodo {
        Objects.requireNonNull(inicio);
        Objects.requireNonNull(fim);
    }

    public boolean noRange(LocalDate date) {
        return !date.isBefore(this.inicio) && !date.isAfter(this.fim);
    }
}
