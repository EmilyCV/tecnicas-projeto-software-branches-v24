package com.pece.agencia.api.core.domain.exception;

import com.pece.agencia.api.core.domain.model.Pacote;

public class PacoteForaValidadeException extends AbstractContratacaoPacoteException {
    public PacoteForaValidadeException(Pacote pacote) {
        super(String.format("Pacote com ID %s está fora do período de validade", pacote.getId()), pacote);
    }
}
