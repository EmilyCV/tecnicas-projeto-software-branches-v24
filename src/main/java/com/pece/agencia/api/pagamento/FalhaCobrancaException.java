package com.pece.agencia.api.pagamento;

public class FalhaCobrancaException extends Exception {
    public FalhaCobrancaException(
            String msg,
            Throwable cause) {

        super(msg, cause);
    }
}

