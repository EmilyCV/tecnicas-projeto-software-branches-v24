package com.pece.agencia.api.core.infrastructure.adapters.acl.aereo;

import com.pece.agencia.api.aereo.PlataformaTransladoAereo;
import com.pece.agencia.api.aereo.PlataformaTransladoAereoOfflineException;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.application.ports.ReservaTransladoAereoService;
import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.DadosVoo;
import com.pece.agencia.api.core.domain.model.ReservaVoo;
import com.pece.agencia.api.core.infrastructure.adapters.acl.aereo.mapper.ReservaTransladoAereoRequestMapper;
import com.pece.agencia.api.core.infrastructure.adapters.acl.aereo.mapper.ReservaVooMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservaTransladoAereoServiceAdapter implements ReservaTransladoAereoService {
    private final ReservaTransladoAereoRequestMapper requestMapper;
    private final ReservaVooMapper requestVooMapper;

    private final PlataformaTransladoAereo service;

    public ReservaVoo reservar(Cliente cliente, DadosVoo dadosVoo, LocalDate data) throws PlataformaParceiroOfflineException {
        try {
            var result = service.reservar(this.requestMapper.toRequest(cliente, dadosVoo, data));
            return requestVooMapper.toReservaVoo(result);
        } catch (PlataformaTransladoAereoOfflineException ex) {
            throw new PlataformaParceiroOfflineException("translado-aereo", ex);
        }
    }
}
