package com.pece.agencia.api.pagamento;

import java.util.Objects;

public record CodigoPagamento(String codigo) {
    public CodigoPagamento {
        Objects.requireNonNull(codigo);
    }
}
