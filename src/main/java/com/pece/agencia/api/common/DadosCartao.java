package com.pece.agencia.api.common;

import java.time.YearMonth;
import java.util.Objects;

public record DadosCartao(String numero, String cvc, YearMonth dataExpiracao) {
    public DadosCartao {
        Objects.requireNonNull(numero);
        Objects.requireNonNull(cvc);
        Objects.requireNonNull(dataExpiracao);
    }
}
