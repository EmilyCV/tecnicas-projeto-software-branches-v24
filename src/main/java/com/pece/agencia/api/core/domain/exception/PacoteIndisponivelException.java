package com.pece.agencia.api.core.domain.exception;

import com.pece.agencia.api.core.domain.model.Pacote;
import lombok.Getter;

@Getter
public class PacoteIndisponivelException extends AbstractContratacaoPacoteException {
    public PacoteIndisponivelException(Pacote pacote) {
        super(String.format("Pacote com ID %s está indisponível", pacote.getId()), pacote);
    }
}
