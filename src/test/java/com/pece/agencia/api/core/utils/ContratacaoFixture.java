package com.pece.agencia.api.core.utils;


import com.pece.agencia.api.core.domain.model.Cliente;
import com.pece.agencia.api.core.domain.model.Contratacao;
import com.pece.agencia.api.core.domain.model.Pacote;
import com.pece.agencia.api.core.domain.repository.ClienteRepository;
import com.pece.agencia.api.core.domain.repository.PacoteRepository;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContratacaoFixture {

    private final ContratacaoTestRepository contratacaoTestRepository;
    private final ClienteRepository clienteRepository;
    private final PacoteRepository pacoteRepository;

    @Transactional
    public Contratacao criarContratacao(String clienteId, String pacoteId, LocalDate momentoCompra) {
        Cliente cliente = clienteRepository.findById(UUID.fromString(clienteId)).get();
        Pacote pacote = pacoteRepository.findById(UUID.fromString(pacoteId)).get();

        Contratacao contratacao = new Contratacao(cliente, pacote, momentoCompra.plusYears(1), Money.of(0, "BRL"));
        contratacao.setMomentoCompra(momentoCompra);

        return contratacaoTestRepository.save(contratacao);
    }

    @Transactional
    public int deleteContratacoesByPacote(String pacoteId, LocalDate since) {
        return this.contratacaoTestRepository.deleteContratacoesByPacote(UUID.fromString(pacoteId), since);
    }
}
