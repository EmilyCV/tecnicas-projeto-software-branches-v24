package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "OFERTA_TRANSLADO_AEREO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OfertaTransladoAereo extends Oferta<CompanhiaAereaParceira> {
    @Embedded
    @AttributeOverride(name = "numero", column = @Column(name = "VOO_IDA_NUMERO"))
    @AttributeOverride(name = "horario", column = @Column(name = "VOO_IDA_HORARIO"))
    private DadosVoo vooIda;

    @Embedded
    @AttributeOverride(name = "numero", column = @Column(name = "VOO_VOLTA_NUMERO"))
    @AttributeOverride(name = "horario", column = @Column(name = "VOO_VOLTA_HORARIO"))
    private DadosVoo vooVolta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANHIA_AEREA_PARCEIRA_ID")
    private CompanhiaAereaParceira parceiroResponsavel;
}
