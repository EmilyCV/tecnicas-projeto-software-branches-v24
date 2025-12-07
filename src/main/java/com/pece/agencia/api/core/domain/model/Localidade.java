package com.pece.agencia.api.core.domain.model;

import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "LOCALIDADE")
@Data
public class Localidade {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NOME_CIDADE")
    private String nomeCidade;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private Estado estado;
}
