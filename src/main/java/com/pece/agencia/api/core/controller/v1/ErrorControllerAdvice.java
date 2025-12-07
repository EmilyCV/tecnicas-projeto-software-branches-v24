package com.pece.agencia.api.core.controller.v1;

import com.pece.agencia.api.common.archunit.MayParseExceptionMessage;
import com.pece.agencia.api.core.application.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.domain.exception.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class ErrorControllerAdvice implements ProblemHandling, SecurityAdviceTrait {
    @MayParseExceptionMessage
    private ResponseEntity.HeadersBuilder<?> forStatus(int statusCode, Throwable ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(statusCode), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleFalhaCobrancaException(final FalhaCobrancaException ex) {
        return forStatus(402, ex).build();
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handlePlataformaParceiroOfflineException(final PlataformaParceiroOfflineException ex) {
        return forStatus(503, ex).header("Retry-After", "10").build();
    }

    @ExceptionHandler(
        exception={
            PacoteNaoEncontradoException.class,
            ClienteNaoEncontradoException.class,
            LocalidadeNaoEncontradaException.class,
            ContratacaoNaoEncontradaException.class
        }
    )
    public ResponseEntity<Problem> handleRecursoNaoEncontrado(final Throwable ex) {
        return forStatus(404, ex).build();
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleAbstractContratacaoPacoteException(final AbstractContratacaoPacoteException ex) {
        return forStatus(422, ex).build();
    }
}
