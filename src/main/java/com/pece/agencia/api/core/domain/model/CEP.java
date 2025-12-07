package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record CEP(@Column(name="CEP") String numero) {
    private static final Pattern CEP_PATTERN = Pattern.compile("^\\d{8}$");
    public CEP {
        if ((numero == null) || !CEP_PATTERN.matcher(numero).matches()) {
            throw new IllegalArgumentException("CEP inválido: " + numero);
        }
    }
}
