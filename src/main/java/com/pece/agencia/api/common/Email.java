package com.pece.agencia.api.common;

import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String endereco) {
    public Email {
        if (endereco == null || !endereco.contains("@")) {
            throw new IllegalArgumentException("Endereço de email inválido: " + endereco);
        }
    }
}
