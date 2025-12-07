package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "HOTEL_PARCEIRO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HotelParceiro extends Parceiro {
    @Embedded
    @AttributeOverride(name = "cep", column = @Column(name = "CEP"))
    @AttributeOverride(name = "logradouro", column = @Column(name = "LOGRADOURO"))
    @AttributeOverride(name = "numero", column = @Column(name = "NUMERO"))
    @AttributeOverride(name = "complemento", column = @Column(name = "COMPLEMENTO"))
    @AttributeOverride(name = "bairro", column = @Column(name = "BAIRRO"))
    @AssociationOverride(name = "localidade", joinColumns =  @JoinColumn(name = "LOCALIDADE_ID"))
    private Endereco endereco;

}

