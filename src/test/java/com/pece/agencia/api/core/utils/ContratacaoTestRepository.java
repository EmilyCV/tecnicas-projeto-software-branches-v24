package com.pece.agencia.api.core.utils;

import com.pece.agencia.api.core.domain.model.Contratacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ContratacaoTestRepository extends  JpaRepository<Contratacao, Long> {

    @Modifying
    @Query("delete Contratacao c where c.pacoteContratado.id = :pacoteId and c.momentoCompra >= :since")
    int deleteContratacoesByPacote(UUID pacoteId, LocalDate since);
}
