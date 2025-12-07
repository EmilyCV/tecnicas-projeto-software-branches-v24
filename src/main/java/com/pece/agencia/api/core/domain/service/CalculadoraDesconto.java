package com.pece.agencia.api.core.domain.service;

import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.Pacote;
import com.pece.agencia.api.core.domain.repository.ContratacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

import static org.apache.commons.lang3.compare.ComparableUtils.max;

@Service
@RequiredArgsConstructor
public class CalculadoraDesconto {
    private final ContratacaoRepository contratacaoRepository;

    public MonetaryAmount getValorDescontoPromocional(Cliente cliente, Pacote pacote, LocalDate dataCompra) {
        MonetaryAmount descontoPacote = pacote.getValorDescontoPromocional(dataCompra, contratacoesEmLote(cliente, pacote));
        MonetaryAmount descontoCliente = cliente.getValorDescontoPromocional(pacote.getPrecoBase(), contratacoesElegiveis(cliente));
        return max(descontoPacote, descontoCliente);
    }

    private int contratacoesElegiveis(Cliente cliente) {
        if (cliente.getPeriodoContratacoesDesconto() == null) {
            return 0;
        } else {
            LocalDate inicioElegibilidade = LocalDate.now().minus(cliente.getPeriodoContratacoesDesconto());
            return this.contratacaoRepository.countContratacoesApos(cliente.getId(), inicioElegibilidade);
        }
    }

    private int contratacoesEmLote(Cliente cliente, Pacote pacote) {
        if (pacote.getPeriodoDescontoLote() == null) {
            return 0;
        } else {
            LocalDate inicioElegibilidade = LocalDate.now().minus(pacote.getPeriodoDescontoLote());
            return this.contratacaoRepository.countContratacoesApos(cliente.getId(), pacote.getId(), inicioElegibilidade);
        }
    }
}