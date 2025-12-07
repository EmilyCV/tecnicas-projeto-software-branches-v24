package com.pece.agencia.api.core.application.exception;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class PlataformaParceiroOfflineException extends Exception {
    private final String plataformaParceiro;

    public PlataformaParceiroOfflineException(
            String plataformaParceiro,
            Throwable cause) {
        super(format("Plataforma %s fora do ar", plataformaParceiro), cause);
        this.plataformaParceiro = plataformaParceiro;
    }
}
