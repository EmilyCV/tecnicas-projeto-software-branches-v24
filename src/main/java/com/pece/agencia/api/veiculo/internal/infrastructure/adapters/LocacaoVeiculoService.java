package com.pece.agencia.api.veiculo.internal.infrastructure.adapters;

import com.pece.agencia.api.common.archunit.MayParseExceptionMessage;
import com.pece.agencia.api.veiculo.CodigoPlataforma;
import com.pece.agencia.api.veiculo.PlataformaLocacaoVeiculo;
import com.pece.agencia.api.veiculo.PlataformaLocacaoVeiculoOfflineException;
import com.pece.agencia.api.veiculo.ReservaVeiculo;
import com.pece.agencia.api.veiculo.internal.domain.model.CodigoPlataformaLocacaoVeiculoMapping;
import com.pece.agencia.api.veiculo.internal.domain.repository.CodigoPlataformaLocacaoVeiculoMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocacaoVeiculoService implements PlataformaLocacaoVeiculo {

    @Value("${plataforma.locadora-veiculos.url}")
    private String plataformaLocadoraVeculosBaseUrl;

    private final CodigoPlataformaLocacaoVeiculoMappingRepository repository;

    private ReservaVeiculo doLocar(ReservaVeiculoRequest request) {
        CodigoPlataformaLocacaoVeiculoMapping mapping = repository.findById(request.codigoLocalidade()).get();

        Map<String, String> payload = new HashMap<>();
        payload.put("motorista", request.motorista().nome());
        payload.put("categoria", request.categoria());
        payload.put("codigo-cidade", mapping.getCodigoPlataformaLocacaoVeiculo().codigo());
        payload.put("data-check-in", request.periodo().inicio().toString());
        payload.put("data-check-out", request.periodo().fim().toString());

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaLocadoraVeculosBaseUrl + "/api/v1/veiculos/reservas", payload, Map.class);

        return new ReservaVeiculo(result.get("numero-reserva"));
    }

    @MayParseExceptionMessage
    public ReservaVeiculo locar(ReservaVeiculoRequest request) throws PlataformaLocacaoVeiculoOfflineException {
        try {
            return doLocar(request);
        } catch (Exception ex) {
            if (ex.getMessage().contains("I/O error on POST request for")) {
                throw new PlataformaLocacaoVeiculoOfflineException(ex);
            } else {
                throw new RuntimeException("Erro generico ao chamar api", ex);
            }
        }
    }

    public CodigoPlataforma obterCodigoPlataformaLocacaoVeiculo(UUID localidade) {
        return repository.findById(localidade)
                         .map(CodigoPlataformaLocacaoVeiculoMapping::getCodigoPlataformaLocacaoVeiculo)
                         .get();
    }
}
