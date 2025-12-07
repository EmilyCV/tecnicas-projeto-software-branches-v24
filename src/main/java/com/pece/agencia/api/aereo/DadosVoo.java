package com.pece.agencia.api.aereo;

import java.time.LocalTime;
import java.util.Objects;

public record DadosVoo(String numero, LocalTime horario) {
    public DadosVoo {
        Objects.requireNonNull(numero);
        Objects.requireNonNull(horario);
    }
}
