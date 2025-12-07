package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public record DadosVoo(String numero, LocalTime horario) {
    public DadosVoo {
        Objects.requireNonNull(numero);
        Objects.requireNonNull(horario);
    }
}
