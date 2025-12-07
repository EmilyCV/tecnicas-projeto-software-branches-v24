package com.pece.agencia.api.veiculo;

import java.util.Objects;

public record Motorista(String nome) {
    public Motorista {
        Objects.requireNonNull(nome);
    }
}
