package com.pece.agencia.api.hotelaria;

import java.util.Objects;

public record ReservaHospedagem(String numero) {
    public ReservaHospedagem {
        Objects.requireNonNull(numero);
    }
}
