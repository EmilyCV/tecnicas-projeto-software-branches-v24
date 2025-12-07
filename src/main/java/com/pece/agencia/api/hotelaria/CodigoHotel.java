package com.pece.agencia.api.hotelaria;


import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record CodigoHotel(String valor) {
    public CodigoHotel {
        Objects.requireNonNull(valor);
    }
}
