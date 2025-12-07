package com.pece.agencia.api.core.domain.model;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.common.hibernate.PeriodToDaysConverter;
import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import com.pece.agencia.api.core.domain.exception.PacoteIndisponivelException;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.sql.Types;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PACOTE")
@Data
public class Pacote {

    private static final MonetaryAmount ZERO_BRL = Money.of(0, "BRL");

    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "DESTINO_ID")
    private Localidade destino;

    @Embedded
    @AttributeOverride(name="valor", column = @Column(name = "DISPONIBILIDADE"))
    private Disponibilidade disponibilidade;

    @Embedded
    @AttributeOverride(name="valor", column = @Column(name = "PERCENTUAL_DESCONTO"))
    private Percentual percentualDesconto;

    @OneToMany
    @JoinColumn(name = "PACOTE_ID")
    private List<Oferta> ofertas;

    @Column(name="DURACAO_VIAGEM")
    @Convert(converter = PeriodToDaysConverter.class)
    private Period duracaoViagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DESCONTO")
    private TipoDescontoPacote tipoDesconto;

    @Embedded
    @AttributeOverride(name = "inicio", column = @Column(name = "INICIO_VALIDADE"))
    @AttributeOverride(name = "fim", column = @Column(name = "FIM_VALIDADE"))
    private Periodo validade;

    public Periodo periodoViagemIniciandoEm(LocalDate dataIda) {
        Periodo periodoViagem = new Periodo(dataIda, dataIda.plus(getDuracaoViagem()));
        return periodoViagem;
    }

    public boolean expiradoEm(LocalDate date) {
        return !this.validade.noRange(date);
    }

    public void garantirDisponibilidade() throws PacoteIndisponivelException {
        try {
            this.setDisponibilidade(this.disponibilidade.decrementar());
        } catch (Disponibilidade.ItemIndisponivelException ex) {
            throw new PacoteIndisponivelException(this);
        }
    }

    public MonetaryAmount getValorDescontoPromocional() {
        return getValorDescontoPromocional(LocalDate.now());
    }

    public MonetaryAmount getValorDescontoPromocional(LocalDate dataCompra) {
        if (this.tipoDesconto == TipoDescontoPacote.FIXO) {
            return this.percentualDesconto.aplicar(getPrecoBase());
        } else if (this.tipoDesconto == TipoDescontoPacote.POR_ANTECIPACAO) {
            long diasAntecedencia = java.time.temporal.ChronoUnit.DAYS.between(dataCompra, this.validade.inicio());
            Percentual descontoAjustado = percentualDesconto.max(percentualDesconto.multiplicar(diasAntecedencia / 30.0));
            return descontoAjustado.aplicar(getPrecoBase());
        } else {
            throw new IllegalArgumentException("Tipo de desconto desconhecido " + this.tipoDesconto);
        }
    }

    public <T extends Oferta> T ofertaDoTipo(Class<T> clazz) {
        return (T) ofertas.stream()
                        .filter(item -> clazz.isInstance(item))
                        .findFirst()
                        .orElse(null);
    }
    public MonetaryAmount getValorTotalAPagar() {
        return getPrecoBase().subtract(getValorDescontoPromocional());
    }

    public MonetaryAmount getPrecoBase() {
        return ofertas.stream()
                      .map(Oferta::getPreco)
                      .reduce(ZERO_BRL, MonetaryAmount::add);
    }
}
