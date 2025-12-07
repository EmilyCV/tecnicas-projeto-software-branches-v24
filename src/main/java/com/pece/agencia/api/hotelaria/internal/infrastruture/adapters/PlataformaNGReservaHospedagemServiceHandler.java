package com.pece.agencia.api.hotelaria.internal.infrastruture.adapters;

import com.pece.agencia.api.hotelaria.CodigoHotel;
import com.pece.agencia.api.hotelaria.Plataforma;
import com.pece.agencia.api.hotelaria.ReservaHospedagem;
import com.pece.agencia.api.hotelaria.internal.application.service.ReservaHospedagemService;
import com.pece.agencia.api.hotelaria.internal.domain.model.CodigoPromocao;
import com.pece.agencia.api.hotelaria.internal.domain.model.OfertaHospedagemPlataformaMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class PlataformaNGReservaHospedagemServiceHandler extends AbstractPlataformaHospedagemServiceHandler {

    @Value("${plataforma.hotel.ng.url}")
    private String plataformaHotelNgBaseUrl;

    public PlataformaNGReservaHospedagemServiceHandler() {
        super(Plataforma.NG);
    }

    private String urlPrimeiraEtapa(OfertaHospedagemPlataformaMapping mapping) {
        CodigoHotel idPlataformaHotel = mapping.getCodigoHotel();
        CodigoPromocao codigoPromocao = mapping.getCodigoPromocao();

        return plataformaHotelNgBaseUrl + "/api/v2/hoteis/" + idPlataformaHotel.valor() + "/promocoes/" + codigoPromocao.valor() + "/reservas";
    }

    private URI primeiroPassoReservaNg(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request) {
        Map <String, Object> requestPayload = new HashMap<>();
        Map <String, String> hospede = new HashMap<>();

        hospede.put("nome", request.hospede().nome());
        hospede.put("data-nascimento", request.hospede().dataNascimento().toString());
        hospede.put("email", request.hospede().email().endereco());
        requestPayload.put("hospede", hospede);
        requestPayload.put("check-in", request.periodo().inicio().toString());
        requestPayload.put("check-out", request.periodo().fim().toString());

        RestTemplate template = new RestTemplate();
        ResponseEntity<Void> result = template.postForEntity(urlPrimeiraEtapa(mapping), requestPayload, Void.class);
        return result.getHeaders().getLocation();
    }


    private String segundoPassoReservaNg(URI novaReserva) {
        URI enderecoNovaReserva = URI.create(this.plataformaHotelNgBaseUrl).resolve(novaReserva);

        RestTemplate template = new RestTemplate();
        Map result = template.getForObject(enderecoNovaReserva, Map.class);
        return String.valueOf(result.get("localizador-reserva"));
    }

    @Override
    public ReservaHospedagem doReservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request) {
        URI novaReserva = this.primeiroPassoReservaNg(mapping, request);
        return new ReservaHospedagem(segundoPassoReservaNg(novaReserva));
    }
}
