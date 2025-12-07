package com.pece.agencia.api.hotelaria.internal.domain.model;

import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import com.pece.agencia.api.hotelaria.CodigoHotel;
import com.pece.agencia.api.hotelaria.Plataforma;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Table(name = "OFERTA_HOSPEDAGEM_PLATAFORMA_MAPPING")
@Data
@Entity
public class OfertaHospedagemPlataformaMapping {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "PLATAFORMA")
    @Enumerated(EnumType.STRING)
    private Plataforma plataforma;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "CODIGO_HOTEL"))
    private CodigoHotel codigoHotel;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "CODIGO_PROMOCAO"))
    private CodigoPromocao codigoPromocao;
}
