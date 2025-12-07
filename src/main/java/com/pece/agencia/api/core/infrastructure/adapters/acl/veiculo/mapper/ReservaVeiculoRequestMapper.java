package com.pece.agencia.api.core.infrastructure.adapters.acl.veiculo.mapper;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.Localidade;
import com.pece.agencia.api.core.domain.model.OfertaLocacaoVeiculo;
import com.pece.agencia.api.veiculo.internal.infrastructure.adapters.LocacaoVeiculoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        MotoristaMapper.class
    }
)
public interface ReservaVeiculoRequestMapper {
    @Mapping(source = "destino.id", target = "codigoLocalidade")
    @Mapping(source = "ofertaLocacaoVeiculo.categoriaVeiculo", target = "categoria")
    @Mapping(source = "cliente", target = "motorista")
    @Mapping(source = "periodoLocacaao", target = "periodo")
    LocacaoVeiculoService.ReservaVeiculoRequest toRequest(OfertaLocacaoVeiculo ofertaLocacaoVeiculo, Localidade destino, Cliente cliente, Periodo periodoLocacaao);
}
