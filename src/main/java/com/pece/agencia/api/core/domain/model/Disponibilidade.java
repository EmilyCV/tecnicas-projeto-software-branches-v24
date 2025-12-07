package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Disponibilidade(int valor) {
    public Disponibilidade {
        if (valor < 0) {
            throw new IllegalArgumentException("Disponibilidade não pode ser negativa");
        }
    }
    Disponibilidade decrementar() throws ItemIndisponivelException {
        if (valor == 0) {
            throw new ItemIndisponivelException();
        } else {
            return new Disponibilidade(valor - 1);
        }
    }

    public static class ItemIndisponivelException extends Exception {
    }
}
