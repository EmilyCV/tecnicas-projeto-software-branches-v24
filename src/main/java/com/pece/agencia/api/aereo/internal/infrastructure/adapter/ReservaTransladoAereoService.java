package com.pece.agencia.api.aereo.internal.infrastructure.adapter;

import com.pece.agencia.api.aereo.PlataformaTransladoAereo;
import com.pece.agencia.api.aereo.PlataformaTransladoAereoOfflineException;
import com.pece.agencia.api.aereo.ReservaVoo;
import com.pece.agencia.api.common.archunit.MayParseExceptionMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReservaTransladoAereoService implements PlataformaTransladoAereo {

    @Value("${plataforma.empresa-aerea.url}")
    private String plataformaEmpresaAereaBaseUrl;

    public ReservaVoo reservar(ReservaTransladoAereoRequest request) throws PlataformaTransladoAereoOfflineException {
        Map <String, String> resultadoReserva = this.doReservar(request.passageiro().nome(), request.dadosVoo().numero(), request.data());

        ReservaVoo reservaVoo = new ReservaVoo(
            resultadoReserva.get("eticket"),
            new ReservaVoo.Assento(resultadoReserva.get("assento")),
            request.data().atTime(request.dadosVoo().horario()),
            request.dadosVoo()
        );

        return reservaVoo;
    }

    @MayParseExceptionMessage
    private Map<String, String> doReservar(String passageiro, String numeroVoo, LocalDate dataIda) throws PlataformaTransladoAereoOfflineException {
        try {
            return doDoReservar(passageiro, numeroVoo, dataIda);
        } catch (Exception ex) {
            if (ex.getMessage().contains("I/O error on POST request for")) {
                throw new PlataformaTransladoAereoOfflineException(ex);
            } else {
                throw new RuntimeException("Erro generico ao chamar api", ex);
            }
        }
    }

    private Map<String, String> doDoReservar(String passageiro, String numeroVoo, LocalDate dataIda) {
        Map<String, String> request = new HashMap<>();
        request.put("passageiro", passageiro);
        request.put("data", dataIda.toString());

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaEmpresaAereaBaseUrl + "/api/v1/voos/" + numeroVoo + "/reservas", request, Map.class);

        return result;
    }
}
