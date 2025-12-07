package com.pece.agencia.api.core.domain.model;

import com.google.i18n.phonenumbers.Phonenumber;
import com.pece.agencia.api.common.Email;
import com.pece.agencia.api.common.hibernate.PeriodToDaysConverter;
import com.pece.agencia.api.common.hibernate.PhoneNumberConverter;
import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.sql.Types;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Entity
@Table(name = "CLIENTE")
@Data
public class Cliente {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Embedded
    @AttributeOverride(name = "endereco", column = @Column(name = "EMAIL"))
    private Email email;

    @Column(name = "TELEFONE")
    @Convert(converter = PhoneNumberConverter.class)
    private Phonenumber.PhoneNumber telefone;

    @Embedded
    @AttributeOverride(name = "cep", column = @Column(name = "CEP"))
    @AttributeOverride(name = "logradouro", column = @Column(name = "LOGRADOURO"))
    @AttributeOverride(name = "numero", column = @Column(name = "NUMERO"))
    @AttributeOverride(name = "complemento", column = @Column(name = "COMPLEMENTO"))
    @AttributeOverride(name = "bairro", column = @Column(name = "BAIRRO"))
    @AssociationOverride(name = "localidade", joinColumns = @JoinColumn(name = "LOCALIDADE_ID"))
    private Endereco endereco;

    public Contratacao contratar(Pacote pacote, LocalDate inicioViagem, MonetaryAmount valorDescontoPromocional) {
        Contratacao contratacao = new Contratacao(this, pacote, inicioViagem, valorDescontoPromocional);
        return contratacao;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DESCONTO")
    private TipoDescontoCliente tipoDesconto;

    @Column(name = "PERIODO_CONTRATACOES_DESCONTO")
    @Convert(converter = PeriodToDaysConverter.class)
    private Period periodoContratacoesDesconto;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "PERCENTUAL_DESCONTO_MAXIMO"))
    private Percentual percentualDescontoMaximo;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "FATOR_DESCONTO"))
    private Percentual fatorDesconto;

    public MonetaryAmount getValorDescontoPromocional(MonetaryAmount precoBase, int contratacoesElegiveis) {
        if (getFatorDesconto() == null) {
            return Money.of(0, precoBase.getCurrency());
        } else {
            Percentual percentualDesconto = this.getFatorDesconto().multiplicar(contratacoesElegiveis);
            Percentual percentualDescontoNormalizado = getPercentualDescontoMaximo().min(percentualDesconto);
            return percentualDescontoNormalizado.aplicar(precoBase);
        }
    }
}
