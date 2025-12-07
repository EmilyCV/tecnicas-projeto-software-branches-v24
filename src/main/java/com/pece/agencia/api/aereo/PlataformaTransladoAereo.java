package com.pece.agencia.api.aereo;

import java.time.LocalDate;
import java.util.Objects;

public interface PlataformaTransladoAereo {
    ReservaVoo reservar(ReservaTransladoAereoRequest request) throws PlataformaTransladoAereoOfflineException;

    record ReservaTransladoAereoRequest(Passageiro passageiro, DadosVoo dadosVoo, LocalDate data) {
        public ReservaTransladoAereoRequest {
            Objects.requireNonNull(passageiro);
            Objects.requireNonNull(dadosVoo);
            Objects.requireNonNull(data);
        }
    }
}
