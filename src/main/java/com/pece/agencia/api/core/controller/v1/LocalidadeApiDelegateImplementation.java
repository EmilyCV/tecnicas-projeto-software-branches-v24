package com.pece.agencia.api.core.controller.v1;

import com.pece.agencia.api.core.application.service.LocalidadeService;
import com.pece.agencia.api.core.controller.v1.dto.Localidade;
import com.pece.agencia.api.core.controller.v1.mapper.LocalidadeMapper;
import com.pece.agencia.api.core.domain.exception.LocalidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocalidadeApiDelegateImplementation implements LocalidadeApiDelegate {
    private final LocalidadeService localidadeService;
    private final LocalidadeMapper localidadeMapper;

    @Override
    public ResponseEntity<Localidade> buscarLocalidadePorId(UUID id) throws LocalidadeNaoEncontradaException {
        return ResponseEntity.ok(localidadeMapper.toDTO(localidadeService.findById(id)));
    }

    @Override
    public ResponseEntity<List<Localidade>> listarTodasLocalidades(Pageable pageable) {
        Page<Localidade> page = localidadeService.findAll(pageable)
                                                 .map(localidadeMapper::toDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
