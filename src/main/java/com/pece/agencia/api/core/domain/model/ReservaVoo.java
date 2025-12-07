package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public record ReservaVoo(String eticket, @Embedded Assento assento, LocalDateTime horarioEmbarque, @Embedded DadosVoo dadosVoo) {
    public ReservaVoo {
        Objects.requireNonNull(eticket);
        Objects.requireNonNull(assento);
        Objects.requireNonNull(horarioEmbarque);
        Objects.requireNonNull(dadosVoo);
    }

    @Embeddable
    public record Assento(String numero) {
        private static final Pattern ASSENTO_VALIDO_REGEX = Pattern.compile("^[0-9]{1,2}-[A-F]$");

        public Assento {
            if (numero == null || !ASSENTO_VALIDO_REGEX.matcher(numero).matches()) {
                throw new IllegalArgumentException("Número de assento inválido: " + numero);
            }
        }
    }
}
