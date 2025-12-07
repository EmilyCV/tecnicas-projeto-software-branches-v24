package com.pece.agencia.api.core.controller.v1;

import com.pece.agencia.api.core.application.service.ContratacaoService;
import com.pece.agencia.api.core.controller.v1.dto.Compra;
import com.pece.agencia.api.core.controller.v1.dto.ContratacaoRequest;
import com.pece.agencia.api.core.controller.v1.mapper.CartaoMapper;
import com.pece.agencia.api.core.controller.v1.mapper.CompraMapper;
import com.pece.agencia.api.core.domain.exception.ContratacaoNaoEncontradaException;
import com.pece.agencia.api.core.domain.model.Contratacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContracacaoApiDelegateImplementation implements ContratacaoApiDelegate {
    private final ContratacaoService contratacaoService;
    private final CompraMapper compraMapper;
    private final CartaoMapper cartaoMapper;

    private Jwt loggedUser() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public ResponseEntity<Void> contratar(UUID pacoteId, ContratacaoRequest request) throws Exception {
        UUID clientId = UUID.fromString(loggedUser().getClaimAsString("sub"));
        Contratacao contratacao = contratacaoService.contratar(pacoteId, clientId, cartaoMapper.toDomain(request.getCartao()), request.getDataIda());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contratacao.getId()) // Assuming YourResource has an getId() method
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Compra> buscarContratacaoPorId(UUID contratacaoId) throws ContratacaoNaoEncontradaException {
        return ResponseEntity.ok(compraMapper.toDTO(contratacaoService.findById(contratacaoId)));
    }
}
