package com.pece.agencia.api.core.application.service;

import com.pece.agencia.api.common.DadosCartao;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.application.ports.GatewayPagamento;
import com.pece.agencia.api.core.application.ports.LocacaoVeiculoService;
import com.pece.agencia.api.core.application.ports.ReservaHospedagemService;
import com.pece.agencia.api.core.application.ports.ReservaTransladoAereoService;
import com.pece.agencia.api.core.domain.exception.*;
import com.pece.agencia.api.core.domain.model.*;
import com.pece.agencia.api.core.domain.repository.ContratacaoRepository;
import com.pece.agencia.api.core.domain.service.CalculadoraDesconto;
import jakarta.validation.constraints.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class ContratacaoService {
    private final ContratacaoRepository contratacaoRepository;
    private final PacoteService pacoteService;
    private final ClienteService clienteService;
    private final GatewayPagamento gatewayPagamento;
    private final ReservaHospedagemService reservaHospedagemService;
    private final LocacaoVeiculoService locacaoVeiculoService;
    private final ReservaTransladoAereoService reservaTransladoAereoService;
    private final CalculadoraDesconto calculadoraDesconto;

    public Contratacao findById(UUID id) throws ContratacaoNaoEncontradaException {
        return this.contratacaoRepository.findById(id)
                                         .orElseThrow(() -> new ContratacaoNaoEncontradaException(id));
    }

    @Transactional(rollbackFor = FalhaCobrancaException.class)
    public Contratacao contratar(UUID pacoteId, UUID clientId, DadosCartao dadosCartao, @Future LocalDate dataIda) throws FalhaCobrancaException, PlataformaParceiroOfflineException, PacoteNaoEncontradoException, PacoteForaValidadeException, PacoteIndisponivelException, ClienteNaoEncontradoException {
        Cliente cliente = this.clienteService.findById(clientId);
        Pacote pacote = this.pacoteService.findById(pacoteId);

        MonetaryAmount valorDescontoPromocional = this.calculadoraDesconto.getValorDescontoPromocional(cliente, pacote, LocalDate.now());

        OfertaTransladoAereo ofertaTransladoAereo = pacote.ofertaDoTipo(OfertaTransladoAereo.class);

        Contratacao contratacao = cliente.contratar(pacote, dataIda, valorDescontoPromocional);
        validar(contratacao);

        contratacao.setCodigoPagamento(this.gatewayPagamento.pagar(dadosCartao, contratacao.getValorPago()));
        contratacao.setReservaHotel(this.reservaHospedagemService.reservar(contratacao.getPacoteContratado().ofertaDoTipo(OfertaHospedagem.class), cliente, contratacao.getPeriodoViagem()));
        contratacao.setReservaVeiculo(this.locacaoVeiculoService.locar(contratacao.getPacoteContratado().ofertaDoTipo(OfertaLocacaoVeiculo.class), cliente, pacote.getDestino(), contratacao.getPeriodoViagem()));

        contratacao.setReservaVooIda(this.reservaTransladoAereoService.reservar(contratacao.getCliente(), ofertaTransladoAereo.getVooIda(), contratacao.getPeriodoViagem().inicio()));
        contratacao.setReservaVooVolta(this.reservaTransladoAereoService.reservar(contratacao.getCliente(), ofertaTransladoAereo.getVooVolta(), contratacao.getPeriodoViagem().fim()));

        this.contratacaoRepository.save(contratacao);

        return contratacao;
    }

    private void validar(Contratacao contratacao) throws PacoteForaValidadeException, PacoteIndisponivelException {
        contratacao.getPacoteContratado().garantirDisponibilidade();

        if (contratacao.getPacoteContratado().expiradoEm(contratacao.getMomentoCompra())) {
            throw new PacoteForaValidadeException(contratacao.getPacoteContratado());
        }
    }
}
