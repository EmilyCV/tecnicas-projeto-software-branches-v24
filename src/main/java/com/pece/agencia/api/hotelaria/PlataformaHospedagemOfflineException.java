package com.pece.agencia.api.hotelaria;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class PlataformaHospedagemOfflineException extends Exception {
    private final Plataforma plataforma;

    public PlataformaHospedagemOfflineException(Plataforma plataforma, Throwable cause) {
        super(format("Erro de conectividade com a plataforma de hospedagem %s", plataforma), cause);
        this.plataforma = plataforma;
    }
}
