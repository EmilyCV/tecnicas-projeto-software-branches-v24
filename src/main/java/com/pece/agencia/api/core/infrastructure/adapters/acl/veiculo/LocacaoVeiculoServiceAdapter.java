package com.pece.agencia.api.core.infrastructure.adapters.acl.veiculo;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.application.ports.LocacaoVeiculoService;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.Localidade;
import com.pece.agencia.api.core.domain.model.OfertaLocacaoVeiculo;
import com.pece.agencia.api.core.domain.model.ReservaVeiculo;
import com.pece.agencia.api.core.infrastructure.adapters.acl.veiculo.mapper.ReservaVeiculoMapper;
import com.pece.agencia.api.core.infrastructure.adapters.acl.veiculo.mapper.ReservaVeiculoRequestMapper;
import com.pece.agencia.api.veiculo.PlataformaLocacaoVeiculo;
import com.pece.agencia.api.veiculo.PlataformaLocacaoVeiculoOfflineException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocacaoVeiculoServiceAdapter implements LocacaoVeiculoService {

    private final ReservaVeiculoMapper reservaMapper;
    private final ReservaVeiculoRequestMapper requestMapper;
    private final PlataformaLocacaoVeiculo service;

    public ReservaVeiculo locar(OfertaLocacaoVeiculo ofertaLocacaoVeiculo, Cliente motorista, Localidade destino, Periodo periodoViagem) throws PlataformaParceiroOfflineException {
        try {
            PlataformaLocacaoVeiculo.ReservaVeiculoRequest request = requestMapper.toRequest(ofertaLocacaoVeiculo, destino, motorista, periodoViagem);
            return reservaMapper.fromDTO(service.locar(request));
        } catch (PlataformaLocacaoVeiculoOfflineException ex) {
            throw new PlataformaParceiroOfflineException("locacao-veiculo", ex);
        }
    }

    public String obterCodigoPlataformaLocacaoVeiculo(UUID destino) {
        return service.obterCodigoPlataformaLocacaoVeiculo(destino)
                      .codigo();
    }
}
