package com.pece.agencia.api.veiculo.internal.domain.model;


import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import com.pece.agencia.api.veiculo.CodigoPlataforma;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Table(name = "CODIGO_PLATAFORMA_LOCACAO_VEICULO_MAPPING")
@Data
@Entity
public class CodigoPlataformaLocacaoVeiculoMapping {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Embedded
    @AttributeOverride(name = "codigo", column = @Column(name = "CODIGO_PLATAFORMA_LOCACAO_VEICULO"))
    private CodigoPlataforma codigoPlataformaLocacaoVeiculo;
}
