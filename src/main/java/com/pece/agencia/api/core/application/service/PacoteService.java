package com.pece.agencia.api.core.application.service;

import com.pece.agencia.api.core.domain.exception.PacoteNaoEncontradoException;
import com.pece.agencia.api.core.domain.model.Contratacao;
import com.pece.agencia.api.core.domain.model.Pacote;
import com.pece.agencia.api.core.domain.repository.ContratacaoRepository;
import com.pece.agencia.api.core.domain.repository.PacoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacoteService {
    private final PacoteRepository pacoteRepository;
    private final ContratacaoRepository contratacaoRepository;


    @Autowired
    public PacoteService(PacoteRepository pacoteRepository, ContratacaoRepository contratacaoRepository) {
        this.pacoteRepository = pacoteRepository;
        this.contratacaoRepository = contratacaoRepository;
    }

    public List<Pacote> findAll() {
        return pacoteRepository.findAll();
    }

    public Pacote findById(UUID id) throws PacoteNaoEncontradoException {
        return pacoteRepository.findById(id)
                               .orElseThrow(() -> new PacoteNaoEncontradoException(id));
    }

    public Page<Pacote> findAll(Pageable pageable) {
        return pacoteRepository.findAll(pageable);
    }

    public Page<Contratacao> findAllCompras(UUID pacoteId, Pageable pageable) {
        return this.contratacaoRepository.findAllByPacote(pacoteId, pageable);
    }

    public Optional<Contratacao> findCompraById(UUID idPacote, UUID idCompra) {
        return this.contratacaoRepository.findById(idCompra)
                .filter(c -> c.getPacoteContratado().getId().equals(idPacote));

    }
}

