package com.pece.agencia.api.aereo;

import java.util.Objects;

public record Passageiro(String nome) {
    public Passageiro {
        Objects.requireNonNull(nome);
    }
}
