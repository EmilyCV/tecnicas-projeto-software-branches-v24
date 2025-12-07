package com.pece.agencia.api.pagamento;

public class FalhaConectividadeException extends Exception {
    public FalhaConectividadeException(
            String msg,
            Throwable cause) {

        super(msg, cause);
    }
}
