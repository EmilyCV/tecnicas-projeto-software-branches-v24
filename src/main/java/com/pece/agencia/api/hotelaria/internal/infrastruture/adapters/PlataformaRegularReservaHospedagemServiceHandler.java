package com.pece.agencia.api.hotelaria.internal.infrastruture.adapters;

import com.pece.agencia.api.hotelaria.Plataforma;
import com.pece.agencia.api.hotelaria.ReservaHospedagem;
import com.pece.agencia.api.hotelaria.internal.application.service.ReservaHospedagemService;
import com.pece.agencia.api.hotelaria.internal.domain.model.OfertaHospedagemPlataformaMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Component
public class PlataformaRegularReservaHospedagemServiceHandler extends AbstractPlataformaHospedagemServiceHandler {
    @Value("${plataforma.hotel.regular.url}")
    private String plataformaHotelRegularBaseUrl;

    public PlataformaRegularReservaHospedagemServiceHandler() {
        super(Plataforma.REGULAR);
    }

    private String url(OfertaHospedagemPlataformaMapping mapping) {
        return plataformaHotelRegularBaseUrl + "/api/v1/hoteis/" + mapping.getCodigoHotel().valor() + "/reservas";
    }


    @Override
    protected ReservaHospedagem doReservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request) {
        Map<String, String> requestPayload = new HashMap<>();

        requestPayload.put("hospede", request.hospede().nome());
        requestPayload.put("data-check-in", request.periodo().inicio().toString());
        requestPayload.put("data-check-out", request.periodo().fim().toString());


        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(url(mapping), requestPayload, Map.class);
        return new ReservaHospedagem(result.get("numero-reserva"));
    }
}
