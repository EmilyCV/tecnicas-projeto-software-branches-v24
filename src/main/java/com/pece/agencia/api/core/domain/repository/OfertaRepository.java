package com.pece.agencia.api.core.domain.repository;

import com.pece.agencia.api.core.domain.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, UUID> {
}
