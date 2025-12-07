package com.pece.agencia.api.hotelaria;

import com.pece.agencia.api.common.Email;

import java.time.LocalDate;
import java.util.Objects;

public record Hospede(String nome, Email email, LocalDate dataNascimento) {
    public Hospede {
        Objects.requireNonNull(nome);
        Objects.requireNonNull(email);
        Objects.requireNonNull(dataNascimento);
    }
}
