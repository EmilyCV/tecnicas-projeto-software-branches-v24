package com.pece.agencia.api.aereo;

public class PlataformaTransladoAereoOfflineException extends Exception {
    public PlataformaTransladoAereoOfflineException(Throwable cause) {
        super("Erro de conectividade com a plataforma de translado aereo", cause);
    }
}
