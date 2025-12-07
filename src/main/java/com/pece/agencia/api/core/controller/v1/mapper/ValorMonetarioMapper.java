package com.pece.agencia.api.core.controller.v1.mapper;

import org.mapstruct.Mapper;

import javax.money.MonetaryAmount;

@Mapper(
        componentModel = "spring"
)
public class ValorMonetarioMapper {
    public Double toDTO(MonetaryAmount valor) {
        if (valor == null) {
            return null;
        } else {
            return valor.getNumber().doubleValue();
        }
    }
}
