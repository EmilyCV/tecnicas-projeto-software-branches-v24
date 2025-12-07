package com.pece.agencia.api.veiculo;


import com.pece.agencia.api.common.Periodo;

import java.util.Objects;
import java.util.UUID;

public interface PlataformaLocacaoVeiculo {
    ReservaVeiculo locar(ReservaVeiculoRequest request) throws PlataformaLocacaoVeiculoOfflineException;
    CodigoPlataforma obterCodigoPlataformaLocacaoVeiculo(UUID localidade);

    record ReservaVeiculoRequest(UUID codigoLocalidade, String categoria, Motorista motorista, Periodo periodo) {
        public ReservaVeiculoRequest {
            Objects.requireNonNull(codigoLocalidade);
            Objects.requireNonNull(categoria);
            Objects.requireNonNull(motorista);
            Objects.requireNonNull(periodo);
        }
    }
}
