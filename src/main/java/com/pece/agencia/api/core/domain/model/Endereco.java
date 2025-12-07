package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public record Endereco(@Embedded CEP cep, @ManyToOne(fetch = FetchType.LAZY) Localidade localidade, String logradouro, String numero, String complemento, String bairro) {
    public Endereco {
        Objects.requireNonNull(cep);
        Objects.requireNonNull(localidade);
        Objects.requireNonNull(logradouro);
        Objects.requireNonNull(numero);
        Objects.requireNonNull(bairro);
    }
}
