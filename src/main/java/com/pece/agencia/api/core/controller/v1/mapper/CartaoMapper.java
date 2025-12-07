package com.pece.agencia.api.core.controller.v1.mapper;


import com.pece.agencia.api.common.DadosCartao;
import com.pece.agencia.api.core.controller.v1.dto.Cartao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.YearMonth;

@Mapper(
    componentModel = "spring"
)
public interface CartaoMapper {
    @Mapping(target="dataExpiracao", source = "validade")
    DadosCartao toDomain(Cartao dto);

    default YearMonth toDomain(String value) {
        return YearMonth.parse(value);
    }
}
