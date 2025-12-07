package com.pece.agencia.api.veiculo;

import java.util.Objects;

public record ReservaVeiculo(String localizador) {
    public ReservaVeiculo {
        Objects.requireNonNull(localizador);
    }
}
